package dev.shaper.akdymall.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Nullability

class ExposedMappingProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String> = emptyMap(),
) : SymbolProcessor {

    private val annotationName = "dev.shaper.akdymall.annotations.ExposedMapping"

    // 수집된 테이블: qualifiedName -> 해당 테이블을 참조한 원본 파일들
    private val collectedTables = sortedMapOf<String, MutableSet<KSFile>>()
    private var tablesFileGenerated = false

    // ksp 옵션으로 커스터마이즈 가능 (build.gradle.kts: ksp { arg("exposedTables.package", "...") })
    private val tablesPackage get() = options["exposedTables.package"] ?: "dev.shaper.akdymall.generated"
    private val tablesObjectName get() = options["exposedTables.objectName"] ?: "ExposedTables"

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(annotationName)
            .filterIsInstance<KSClassDeclaration>()
            .forEach { decl ->
                runCatching { generate(decl) }
                    .onFailure { e -> logger.error("ExposedMapping generation failed: ${e.message}", decl) }
            }
        return emptyList()
    }

    override fun finish() {
        if (collectedTables.isEmpty() || tablesFileGenerated) return
        tablesFileGenerated = true
        runCatching { generateTablesFile() }
            .onFailure { e -> logger.error("ExposedTables generation failed: ${e.message}") }
    }

    private fun generateTablesFile() {
        val originatingFiles = collectedTables.values.flatten().distinct()

        val file = codeGenerator.createNewFile(
            // 모든 어노테이션 파일에 의존하는 집계 파일이므로 aggregating = true
            dependencies = Dependencies(aggregating = true, *originatingFiles.toTypedArray()),
            packageName = tablesPackage,
            fileName = tablesObjectName,
        )

        file.bufferedWriter().use { w ->
            w.appendLine("package $tablesPackage")
            w.appendLine()
            w.appendLine("import org.jetbrains.exposed.v1.core.Table")
            w.appendLine("import org.jetbrains.exposed.v1.jdbc.SchemaUtils")
            w.appendLine()
            w.appendLine("object $tablesObjectName {")
            w.appendLine()
            w.appendLine("    val all: List<Table> = listOf(")
            collectedTables.keys.forEach { qualified ->
                w.appendLine("        $qualified,")
            }
            w.appendLine("    )")
            w.appendLine()
            w.appendLine("    /**")
            w.appendLine("     * 반드시 transaction {} 블록 안에서 호출해야 합니다.")
            w.appendLine("     */")
            w.appendLine("    fun createAll(inBatch: Boolean = false) {")
            w.appendLine("        SchemaUtils.create(tables = all.toTypedArray(), inBatch = inBatch)")
            w.appendLine("    }")
            w.appendLine("}")
        }
    }

    private fun stringArrayArg(ann: com.google.devtools.ksp.symbol.KSAnnotation, name: String): List<String> =
        ann.arguments
            .firstOrNull { it.name?.asString() == name }
            ?.let { (it.value as? List<*>)?.filterIsInstance<String>() }
            .orEmpty()

    private fun generate(clazz: KSClassDeclaration) {
        val pkg = clazz.packageName.asString()
        val className = clazz.simpleName.asString()
        val idPropName = className.replaceFirstChar { it.lowercase() } + "Id"
        val ann = clazz.annotations.first { it.shortName.asString() == "ExposedMapping" }

        val tableType = ann.arguments
            .first { it.name?.asString() == "table" }.value as KSType
        val tableDecl = tableType.declaration as KSClassDeclaration
        val tableQualified = tableDecl.qualifiedName!!.asString()
        val tableSimple = tableDecl.simpleName.asString()

        // ---- 테이블 수집 (finish()에서 ExposedTables 파일 생성에 사용) ----
        if (tableDecl.classKind == ClassKind.OBJECT) {
            val files = collectedTables.getOrPut(tableQualified) { mutableSetOf() }
            clazz.containingFile?.let { files.add(it) }
            tableDecl.containingFile?.let { files.add(it) }
        } else {
            logger.warn("$tableQualified is not an object declaration; skipped in $tablesObjectName", tableDecl)
        }
        // -----------------------------------------------------------------

        val superNames = tableDecl.superTypes
            .map { it.resolve().declaration.simpleName.asString() }
            .toList()
        val autoIncrementIdTables = setOf("LongIdTable", "IntIdTable")
        val assignId = superNames.none { it in autoIncrementIdTables }
        val overrides = stringArrayArg(ann, "overrides")
            .associate { it.substringBefore(":") to it.substringAfter(":") }
        val references = stringArrayArg(ann, "references").toSet()
        val readOnly = stringArrayArg(ann, "readOnly").toSet()

        val props = clazz.primaryConstructor!!.parameters.map { param ->
            val name = param.name!!.asString()
            val isId = param.hasAnn("Id")
            PropInfo(
                name = name,
                nullable = param.type.resolve().nullability == Nullability.NULLABLE,
                isId = isId,
                assignId = isId && (param.annArg("Id", "assign") as? Boolean ?: false),
                isReference = param.hasAnn("Reference"),
                isReadOnly = param.hasAnn("ReadOnly"),
                column = (param.annArg("Column", "name") as? String) ?: name,
            )
        }

        fun colOf(prop: String) = overrides[prop] ?: prop

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, clazz.containingFile!!),
            packageName = pkg,
            fileName = "${className}Mapping",
        )

        file.bufferedWriter().use { w ->
            w.appendLine("package $pkg")
            w.appendLine()
            w.appendLine("import org.jetbrains.exposed.v1.core.ResultRow")
            w.appendLine("import org.jetbrains.exposed.v1.core.dao.id.EntityID")
            w.appendLine("import org.jetbrains.exposed.v1.core.statements.UpdateBuilder")
            w.appendLine("import $tableQualified")
            w.appendLine()

            // from<ClassName>() : id 와 readOnly 제외
            w.appendLine("fun UpdateBuilder<*>.from$className(p: $className) {")
            props.filter { info ->
                !info.isReadOnly && (!info.isId || info.assignId)   // id는 assign=true일 때만 포함
            }.forEach { info ->
                val col = if (info.isId) "id" else info.column
                val rhs = "p.${info.name}"
                w.appendLine("    this[$tableSimple.$col] = $rhs")
            }
            w.appendLine("}")
            w.appendLine()

            // to<ClassName>()
            w.appendLine("fun ResultRow.to$className(): $className = $className(")
            props.forEachIndexed { i, info ->
                val comma = if (i == props.lastIndex) "" else ","
                val vle = if (info.nullable) "?.value" else ".value"
                val expr = when {
                    info.isId -> "this[$tableSimple.id]$vle"
                    info.isReference -> "this[$tableSimple.${info.column}]$vle"
                    else -> "this[$tableSimple.${info.column}]"
                }
                w.appendLine("    ${info.name} = $expr$comma")
            }
            w.appendLine(")")
        }
    }
}