package dev.shaper.akdymall.features.data.user

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

object UserTable : UUIDTable("users") {
    val username = varchar("username", 255)
    val token = varchar("token", 255).nullable()
    val name = varchar("name", 255)
    val email = varchar("email", 255)
    val phoneNumber = varchar("phone", 255)
    val zipCode = varchar("zip_code", 255).nullable()
    val roadAddress = varchar("road_address", 255).nullable()
    val detailAddress = varchar("detail_address", 255).nullable()
    val level = integer("level")
    val role = enumeration("role", UserRole::class)
    val status = enumeration("status", UserStatus::class)
    val points = long("points")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
    val expiresAt = datetime("expires_at").clientDefault {
        Clock.System.now()
            .plus(365.days)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }
}