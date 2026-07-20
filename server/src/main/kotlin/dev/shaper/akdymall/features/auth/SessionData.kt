package dev.shaper.akdymall.features.auth

import java.util.UUID

data class SessionData(
    val userId: UUID,
    val role: Int,
)
