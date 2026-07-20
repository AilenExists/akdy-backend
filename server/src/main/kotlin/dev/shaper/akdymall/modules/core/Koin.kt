package dev.shaper.akdymall.modules.core

import dev.shaper.akdymall.services.di.AppModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(AppModule.databaseModule)
        modules(AppModule.appModule)
    }
}
