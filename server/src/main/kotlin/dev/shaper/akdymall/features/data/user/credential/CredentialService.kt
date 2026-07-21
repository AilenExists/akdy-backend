package dev.shaper.akdymall.features.data.user.credential

import dev.shaper.akdymall.features.data.user.UserTable
import dev.shaper.akdymall.features.data.user.identity.FlatIdentity
import dev.shaper.akdymall.features.data.user.identity.Identity
import dev.shaper.akdymall.features.data.user.identity.IdentityTable
import dev.shaper.akdymall.features.data.user.identity.toIdentity
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

class CredentialService {

    fun createUserIdentity(identity: FlatIdentity) : Identity = transaction {
        val id = IdentityTable.insertAndGetId {
            it[userId] = identity.userId
            it[zipCode] = identity.zipCode
            it[roadAddress] = identity.roadAddress
            it[detailAddress] = identity.detailAddress
            it[name] = identity.name
            it[age]  = identity.age
            it[gender] = identity.gender
        }
        IdentityTable.selectAll()
            .where { IdentityTable.id eq id }
            .single()
            .toIdentity()
    }

    fun findUserCredentialByEmail(email: String) : Credential? = transaction {
    (CredentialTable innerJoin UserTable)
        .selectAll()
        .where { UserTable.email eq email }
        .map { it.toCredential() }
        .firstOrNull()
}

    fun findUserCredentialByUserId(provider: CredentialProvider,providerUserId:String): Credential? = transaction {
        CredentialTable.selectAll()
            .where { CredentialTable.providerUserId eq providerUserId }
            .singleOrNull()
            ?.toCredential()
    }

    fun findUserCredential(userId: UUID) : Credential? = transaction {
        CredentialTable.selectAll()
            .where { CredentialTable.userId eq userId }
            .singleOrNull()
            ?.toCredential()
    }
}