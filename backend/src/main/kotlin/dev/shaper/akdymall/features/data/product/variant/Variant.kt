package dev.shaper.akdymall.features.data.product.variant

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.Reference
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement

@ExposedMapping(VariantTable::class)
data class Variant(
    @Id val variantId: Long,
    @Reference val productId: Long,
    val count: Long,
    val model: String,
    val color : String,
    val data : JsonElement?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
