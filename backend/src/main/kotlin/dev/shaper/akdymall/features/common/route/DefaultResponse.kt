package dev.shaper.akdymall.features.common.route

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse (
    override val message: String,
) : BaseResponse {
}