package dev.shaper.akdymall.features.data.product.specs

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement

data class Spec(
    val specId: Long,
    val productId: Long,
    val productData : JsonElement,
    val validationData: JsonElement,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
