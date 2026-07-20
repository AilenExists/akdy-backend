package dev.shaper.akdymall.features.data.user

import java.util.UUID

data class FlatUser(
    val id: UUID,
    val role: UserRole,
    override val username: String,
    override val email: String,
    override val phoneNumber: String,
) : BaseUser
