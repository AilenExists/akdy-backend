package dev.shaper.akdymall.features.auth

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val zipCode: String?,
    val roadAddress: String?
)
