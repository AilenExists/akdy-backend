package dev.shaper.akdymall.modules.core

import dev.shaper.akdymall.module
import dev.shaper.akdymall.services.di.AppModule
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                single<Application> { this@configureKoin }
                single<ApplicationConfig> { this@configureKoin.environment.config }
            },
        )
        modules(AppModule.databaseModule)
        modules(AppModule.appModule)
    }
}
