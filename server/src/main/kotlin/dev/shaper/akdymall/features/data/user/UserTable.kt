package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.features.common.database.BaseExpireTable
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object UserTable : UUIDTable("users"), BaseExpireTable {
    val username = varchar("username", 255).uniqueIndex()
    val token = varchar("token", 255).nullable()
    val email = varchar("email", 255).uniqueIndex()
    val phoneNumber = varchar("phone", 255)
    val role = enumeration("role", UserRole::class)
    val status = enumeration("status", UserStatus::class)
    val points = long("points")
    val likes = array<Long>("likes")
    val profile = varchar("profile", 255).nullable()
}