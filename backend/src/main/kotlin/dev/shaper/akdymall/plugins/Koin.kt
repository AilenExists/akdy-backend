package dev.shaper.akdymall.plugins

import dev.shaper.akdymall.di.AppModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(AppModule.databaseModule)
    }
}
