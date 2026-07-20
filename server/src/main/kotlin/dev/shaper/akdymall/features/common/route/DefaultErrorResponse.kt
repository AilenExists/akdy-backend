package dev.shaper.akdymall.features.common.route

data class DefaultErrorResponse (
    override val message: String,
    val reason: ErrorCode,
) : BaseResponse