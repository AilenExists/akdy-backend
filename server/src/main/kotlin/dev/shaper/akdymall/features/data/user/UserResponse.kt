package dev.shaper.akdymall.features.data.user

import dev.shaper.akdymall.features.common.route.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse (
    override val username: String,
    override val email: String,
    override val phoneNumber: String,
    val role: UserRole,
    val points: Long,
    val likes: List<Long>,
    val profile: String?,
): BaseUser{
}