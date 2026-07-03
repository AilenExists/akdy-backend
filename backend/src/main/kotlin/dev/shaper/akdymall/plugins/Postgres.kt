package dev.shaper.akdymall.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


fun Application.configurePostgres() {
    val hikariConfig = HikariConfig().apply {
        jdbcUrl = environment.config.property("postgres.url").getString()
        username = environment.config.property("postgres.username").getString()
        password = environment.config.property("postgres.password").getString()
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
    }

    try {
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)
        environment.log.info("Database connected successfully!")
    } catch (e: Exception) {
        environment.log.error("Database connection failed: ${e.message}")
    }

    transaction {
        //추후 Flyway 기술 추가
    }
}
