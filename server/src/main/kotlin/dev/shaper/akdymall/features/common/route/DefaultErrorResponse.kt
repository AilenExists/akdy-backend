package dev.shaper.akdymall.features.common.route

import kotlinx.serialization.Serializable

@Serializable
data class DefaultErrorResponse (
    override val message: String,
    val reason: ErrorCode,
) : BaseResponse