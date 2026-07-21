plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "dev.shaper"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}


dependencies {
    implementation(project(":annotations"))
    ksp(project(":processor"))
    implementation(ktorLibs.client.apache)
    implementation(ktorLibs.client.core)
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.auth.jwt)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.cors)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.statusPages)
    implementation(libs.exposed.core)
    implementation(libs.exposed.r2dbc)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.exposed.json)
    implementation(libs.hikaricp)
    implementation(libs.h2database.h2)
    implementation(libs.h2database.r2dbc)
    implementation(libs.okhttp.ktor)
    implementation(libs.koin.ktor)
    implementation(libs.elastic.search)
    implementation(libs.elastic.search.client)
    implementation(libs.koin.loggerSlf4j)
    implementation(libs.logback.classic)
    implementation(libs.postgresql)
    implementation(libs.ucasoft.ktorSimpleCache)
    implementation(libs.ucasoft.ktorSimpleRedisCache)
    implementation(libs.lettuce)
    implementation(libs.jbcrypt)

    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}

val dotenv: Map<String, String> = rootProject.file("../.secrets/.env.test").takeIf { it.exists() }
    ?.readLines()
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() && !it.startsWith("#") && it.contains("=") }
    ?.associate {
        val (k, v) = it.split("=", limit = 2)
        k.trim() to v.trim().removeSurrounding("\"").removeSurrounding("'")
    } ?: emptyMap()

tasks.withType<Test>().configureEach {
    dotenv.forEach { (k, v) -> environment(k, v) }
}