package dev.shaper.akdymall.modules.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.shaper.akdymall.generated.ExposedTables
import dev.shaper.akdymall.services.di.AppModule.databaseModule
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.core.SqlLogger
import org.jetbrains.exposed.v1.core.Transaction
import org.jetbrains.exposed.v1.core.statements.StatementContext
import org.jetbrains.exposed.v1.core.statements.expandArgs
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import javax.sql.DataSource


fun Application.configurePostgres() {
    val hikariConfig = HikariConfig().apply {
        jdbcUrl = environment.config.property("postgres.url").getString()
        username = environment.config.property("postgres.username").getString()
        password = environment.config.property("postgres.password").getString()
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
    }
    var dataSource: DataSource? = null
    var database: Database? = null

    try {
        dataSource = HikariDataSource(hikariConfig)
        database = Database.connect(dataSource)
        environment.log.info("Database connected successfully!")
    } catch (e: Exception) {
        environment.log.error("Database connection failed: ${e.message}")
    }

    transaction {
        //추후 Flyway 기술 추가
        ExposedTables.createAll()
    }

    loadKoinModules(module {
        single<DataSource> { dataSource!! }
        single<Database> { database!! }
    })
}
