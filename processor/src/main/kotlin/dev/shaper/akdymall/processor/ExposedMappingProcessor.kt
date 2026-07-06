package dev.shaper.akdymall.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Nullability

class ExposedMappingProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private val annotationName = "dev.shaper.akdymall.annotations.ExposedMapping"

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(annotationName)
            .filterIsInstance<KSClassDeclaration>()
            .forEach { decl ->
                runCatching { generate(decl) }
                    .onFailure { e -> logger.error("ExposedMapping generation failed: ${e.message}", decl) }
            }
        return emptyList()
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
                val rhs = if (info.nullable) "p.${info.name}!!" else "p.${info.name}"
                w.appendLine("    this[$tableSimple.$col] = $rhs")
            }
            w.appendLine("}")
            w.appendLine()

            // to<ClassName>()
            w.appendLine("fun ResultRow.to$className(): $className = $className(")
            props.forEachIndexed { i, info ->
                val comma = if (i == props.lastIndex) "" else ","
                val expr = when {
                    info.isId -> "this[$tableSimple.id].value"
                    info.isReference -> "this[$tableSimple.${info.column}].value"
                    else -> "this[$tableSimple.${info.column}]"
                }
                w.appendLine("    ${info.name} = $expr$comma")
            }
            w.appendLine(")")
        }
    }
}