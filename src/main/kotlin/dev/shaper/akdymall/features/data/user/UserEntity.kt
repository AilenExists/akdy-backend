package dev.shaper.akdymall.features.data.user

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

import java.util.UUID

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    object UserTable : UUIDTable("users") {
        val username = varchar("username", 255)
        val password = varchar("password", 255)
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
    }

    companion object: UUIDEntityClass<UserEntity>(UserTable)
    var username by UserTable.username
    var password by UserTable.password
    var token by UserTable.token
    var name by UserTable.name
    var email by UserTable.email
    var phoneNumber by UserTable.phoneNumber
    var zipCode by UserTable.zipCode
    var roadAddress by UserTable.roadAddress
    var detailAddress by UserTable.detailAddress
    var level by UserTable.level
    var role by UserTable.role
    var status by UserTable.status
    var points by UserTable.points
    var createdAt by UserTable.createdAt
    var updatedAt by UserTable.updatedAt

    fun toUser() = User(
        id.value,
        username,
        password ,
        token,
        name ,
        email ,
        phoneNumber ,
        zipCode ,
        roadAddress ,
        detailAddress ,
        level ,
        status ,
        role ,
        points ,
        createdAt ,
        updatedAt 
    )
}