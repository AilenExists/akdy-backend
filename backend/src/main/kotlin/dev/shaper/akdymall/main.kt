package dev.shaper.akdymall

import dev.shaper.akdymall.modules.core.configureKoin
import dev.shaper.akdymall.modules.database.configurePostgres
import dev.shaper.akdymall.modules.database.configureRedis
import dev.shaper.akdymall.modules.route.configureRouting
import dev.shaper.akdymall.modules.route.configureSecurity
import dev.shaper.akdymall.modules.route.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configurePostgres()
    configureRedis()
    configureRouting()
    configureSecurity()
    configureStatusPages()
}