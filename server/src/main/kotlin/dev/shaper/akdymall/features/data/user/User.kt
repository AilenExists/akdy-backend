package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.features.common.database.BaseExpireDTO
import dev.shaper.akdymall.utils.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@ExposedMapping(UserTable::class)
data class User (
    @Id(true)
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    override val username: String,
    override val email: String,
    override val phoneNumber: String,
    val role: UserRole,
    val token: String?,
    val status: UserStatus,
    val points: Long,
    val likes: List<Long>,
    val profile: String?,
    @ReadOnly override val createdAt: LocalDateTime,
    @ReadOnly override val updatedAt: LocalDateTime,
    @ReadOnly override val expiresAt: LocalDateTime,
): BaseExpireDTO, BaseUser {
}