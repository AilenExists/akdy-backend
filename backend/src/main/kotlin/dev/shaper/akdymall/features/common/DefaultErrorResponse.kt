package dev.shaper.akdymall.features.common

data class DefaultErrorResponse (
    override val status: Int,
    override val message: String,
    val reason: String,
) : BaseResponse