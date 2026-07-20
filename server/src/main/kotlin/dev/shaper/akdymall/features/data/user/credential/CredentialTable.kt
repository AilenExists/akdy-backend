package dev.shaper.akdymall.features.data.user.credential

import dev.shaper.akdymall.features.data.user.UserTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object CredentialTable : LongIdTable("user_credentials") {
    val name = varchar("name", 255)
    val userId = reference("user_id", UserTable)
    val provider = enumeration<CredentialProvider>("provider")
    val providerUserId = varchar("provider_user_id", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)

    init {
        uniqueIndex(provider,providerUserId)
    }
}