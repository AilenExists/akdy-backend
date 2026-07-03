package dev.shaper.akdymall.features.data.product.specs

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement

@ExposedMapping(SpecTable::class)
data class Spec(
    @Id val specId: Long,
    @Reference val productId: Long,
    val productData : JsonElement,
    val validationData: JsonElement,
    @ReadOnly val createdAt: LocalDateTime,
    @ReadOnly val updatedAt: LocalDateTime
)
