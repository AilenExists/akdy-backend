package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.features.data.product.ProductTable.isDeleted
import dev.shaper.akdymall.utils.ValueUtils.nowTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.util.*

class UserService {

    fun createUser(user: FlatUser): User = transaction {
        val id = UserTable.insertAndGetId {
            it[id]          = user.id
            it[username]    = user.username
            it[email]       = user.email
            it[phoneNumber] = user.phoneNumber
            it[status]      = UserStatus.PENDING
            it[role]        = user.role
            it[points]      = 100
            it[likes]       = listOf()
        }
        UserTable.selectAll()
            .where { UserTable.id eq id }
            .single()
            .toUser()
    }

    fun findUserById(userId: UUID) : User? = transaction {
        UserTable.selectAll()
            .where { UserTable.id eq userId }
            .singleOrNull()
            ?.toUser()
    }

    fun findUserByEmail(email: String) : User? = transaction {
        UserTable.selectAll()
            .where { UserTable.email eq email }
            .singleOrNull()
            ?.toUser()
    }

    fun updateUser(user : User): Boolean = transaction {
        val updated = UserTable.update({ UserTable.id eq user.userId }) {
            it.fromUser(user)
            it[updatedAt] = nowTime()
        }
        updated > 0
    }

    fun deleteUser(userId : UUID): Boolean = transaction {
        // Do not delete User Immediately
        // We have to delete user by status,updatedAt
        val updated = UserTable.update({ UserTable.id eq userId }) {
            it[isDeleted] = true
            it[updatedAt] = nowTime()
        }
        updated > 0
    }

}