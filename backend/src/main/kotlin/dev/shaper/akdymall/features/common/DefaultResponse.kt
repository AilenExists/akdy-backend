package dev.shaper.akdymall.features.common

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse (
    override val status: Int,
    override val message: String,
) : BaseResponse {
}