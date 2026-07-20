plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
}

val dotenv: Map<String, String> = rootProject.file("../.secrets/.env.test").takeIf { it.exists() }
    ?.readLines()
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() && !it.startsWith("#") && it.contains("=") }
    ?.associate {
        val (key, value) = it.split("=", limit = 2)
        key.trim() to value.trim().removeSurrounding("\"").removeSurrounding("'")
    } ?: emptyMap()

tasks.withType<JavaExec>().configureEach {
    dotenv.forEach { (k, v) -> environment(k, v) }
}

tasks.withType<Test>().configureEach {
    dotenv.forEach { (k, v) -> environment(k, v) }
}