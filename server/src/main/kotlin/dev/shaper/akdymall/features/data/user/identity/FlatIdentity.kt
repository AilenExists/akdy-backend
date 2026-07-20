package dev.shaper.akdymall.features.data.user.identity

import java.util.UUID

data class FlatIdentity(
    val userId: UUID,
    override val zipCode: String?,
    override val roadAddress: String?,
    override val detailAddress: String?,
    override val name: String,
    override val age: Short,
    override val gender: UserGender,
): BaseIdentity
