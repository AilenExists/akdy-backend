package dev.shaper.akdymall

import dev.shaper.akdymall.plugins.configurePostgres
import dev.shaper.akdymall.plugins.configureRouting
import dev.shaper.akdymall.plugins.configureSecurity
import dev.shaper.akdymall.plugins.configureSerialization
import dev.shaper.akdymall.plugins.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configurePostgres()
    configureRouting()
    configureSecurity()
    configureStatusPages()
}