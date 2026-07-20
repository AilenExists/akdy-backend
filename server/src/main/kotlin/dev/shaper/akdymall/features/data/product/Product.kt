package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import dev.shaper.akdymall.features.common.database.BaseDTO
import dev.shaper.akdymall.utils.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.JsonElement
import java.util.UUID

@Serializable
@ExposedMapping(ProductTable::class)
data class Product (
    @Id         val productId: Long,    //PK
    @Reference  val categoryId: Long?,  //FK
    val tags: JsonElement,
    val name: String,
    val description: String,
    val badges: List<String>,
    val certQuote: ProductCert,
    val sellerName: String,
    @Serializable(UUIDSerializer::class)
    @Reference val sellerId: UUID,
    val regularPrice: Double,
    val salePrice: Double,
    val images: List<String>,
    val buyMin: Int,
    val buyMax: Int,
    val ratingSum: Double,
    val ratingCount: Int,
    val location: String,
    val tier: ProductGrade,
    val isDeleted: Boolean,
    @ReadOnly override val createdAt: LocalDateTime,
    @ReadOnly override val updatedAt: LocalDateTime,
    ) : BaseDTO