package dev.shaper.akdymall.features.data.product.variant

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement

data class Variant(
    val variantId: Long,
    val productId: Long,
    val count: Long,
    val model: String,
    val color : String,
    val data : JsonElement?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
