package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
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
    val username: String,
    val token: String?,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val zipCode: String?,
    val roadAddress: String?,
    val detailAddress: String?,
    val level: Int,
    val status: UserStatus,
    val role: UserRole,
    val points: Long,
    @ReadOnly
    val createdAt: LocalDateTime,
    @ReadOnly
    val updatedAt: LocalDateTime,
    @ReadOnly
    val expiresAt: LocalDateTime?,
){
}