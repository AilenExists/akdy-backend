package dev.shaper.akdymall.features.data.review

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import dev.shaper.akdymall.features.common.database.BaseDTO
import dev.shaper.akdymall.utils.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import java.util.*

@Serializable
@ExposedMapping(ReviewTable::class)
data class Review(
    @Id val reviewId: Long,
    @Reference val productId: Long,
    @Serializable(with = UUIDSerializer::class)
    @Reference val userId: UUID,
    val rating: Short,          // (1~10 / 2) = 0.5~5.0
    val title: String,
    val content: String,
    val images: JsonElement,
    val likes: Int,
    val isBest: Boolean,
    val isDeleted: Boolean,
    @ReadOnly override val createdAt: LocalDateTime,
    @ReadOnly override val updatedAt: LocalDateTime
    ): BaseDTO
