package dev.shaper.akdymall.features.data.product.specs

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import dev.shaper.akdymall.features.structure.database.BaseDTO
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement

@ExposedMapping(SpecTable::class)
data class Spec(
    @Id val specId: Long,
    @Reference val productId: Long,
    val productData : JsonElement,
    val validationData: JsonElement,
    @ReadOnly override val createdAt: LocalDateTime,
    @ReadOnly override val updatedAt: LocalDateTime
): BaseDTO
