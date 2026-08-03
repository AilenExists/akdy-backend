package dev.shaper.akdymall.features.auth

import dev.shaper.akdymall.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SessionData(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val role: Int,
)
