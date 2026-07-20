package dev.shaper.akdymall.features.data.user.credential

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import kotlinx.datetime.LocalDateTime
import java.util.UUID

//@Serializable
@ExposedMapping(CredentialTable::class)
data class Credential (
    @Id
    val id: Long,       //PK
    @Reference
    val userId: UUID,   //FK
    val provider: CredentialProvider,
    val providerUserId: String,
    val passwordHash: String,
    @ReadOnly
    val updatedAt: LocalDateTime,
    @ReadOnly
    val createdAt: LocalDateTime,
)