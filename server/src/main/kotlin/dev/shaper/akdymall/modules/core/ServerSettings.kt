package dev.shaper.akdymall.modules.core

import dev.shaper.akdymall.services.settings.LogSettings
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun Application.configureServerSettings() {

    val logger = KotlinLogging.logger {}
    val settings = LogSettings(logger)
    settings.apply {
        printHandler()
        errorHandler()
    }

    loadKoinModules(module {
        single { settings }
    })

}