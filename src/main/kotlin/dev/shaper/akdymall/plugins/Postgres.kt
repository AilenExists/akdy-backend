package dev.shaper.akdymall.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.shaper.akdymall.features.data.product.ProductEntity
import dev.shaper.akdymall.features.data.product.category.CategoryEntity
import dev.shaper.akdymall.features.data.product.specs.SpecEntity
import dev.shaper.akdymall.features.data.product.variant.VariantEntity
import dev.shaper.akdymall.features.data.user.UserEntity
import dev.shaper.akdymall.features.view.banner.BannerEntity
import io.ktor.server.application.*
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
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
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(BannerEntity.BannerTable)
        SchemaUtils.create(ProductEntity.ProductTable)
        SchemaUtils.create(CategoryEntity.CategoryTable)
        SchemaUtils.create(SpecEntity.SpecTable)
        SchemaUtils.create(VariantEntity.VariantTable)
        SchemaUtils.create(UserEntity.UserTable)
        //추후 Flyway 기술 추가
    }
}
