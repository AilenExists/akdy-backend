package dev.shaper.akdymall.features.auth

import dev.shaper.akdymall.features.data.user.BaseUser
import dev.shaper.akdymall.features.data.user.identity.BaseIdentity
import dev.shaper.akdymall.features.data.user.identity.UserGender

data class RegisterRequest(
    override val username: String,
    override val name: String,
    override val email: String,
    override val phoneNumber: String,
    override val zipCode: String?,
    override val roadAddress: String?,
    override val detailAddress: String?,
    override val age: Short,
    override val gender : UserGender,
    val password: String,
) : BaseUser, BaseIdentity
