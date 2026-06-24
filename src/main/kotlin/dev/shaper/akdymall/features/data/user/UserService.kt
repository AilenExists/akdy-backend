package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.utils.ExposedUtils.copyFrom
import dev.shaper.akdymall.utils.ValueUtils.nowTime
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

class UserService {

    fun createUser(user: User): User = transaction {
        UserEntity.new {
            copyFrom(user)
        }.toUser()
    }

    fun readUserById(userId: UUID) : User? = transaction {
        UserEntity.findById(userId)?.toUser()
    }

    fun updateUser(user : User): Boolean = transaction {
        val entity = UserEntity.findById(user.userId) ?: return@transaction false
        entity.copyFrom(user) {
            this.updatedAt = nowTime()
        }
        true

    }

    fun deleteUser(userId : UUID): Boolean = transaction {
        // Do not delete User Immediately
        // We have to delete user by status,updatedAt
        val entity = UserEntity.findById(userId) ?: return@transaction false
        entity.status = UserStatus.DELETED
        entity.updatedAt = nowTime()
        true
    }

}