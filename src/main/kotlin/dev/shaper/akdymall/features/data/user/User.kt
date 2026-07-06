package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.utils.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User (
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val username: String,
    val password: String,
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
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
){
}