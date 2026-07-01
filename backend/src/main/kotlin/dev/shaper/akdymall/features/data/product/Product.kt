package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
@ExposedMapping(ProductTable::class)
data class Product (
    @Id         val productId: Long,    //PK
    @Reference  val categoryId: Long?,  //FK
    val tags: JsonElement,
    val name: String,
    val description: String,
    val regularPrice: Double,
    val salePrice: Double,
    val buyMin: Int,
    val buyMax: Int,
    val images: List<String>,
    val manufacture: String,
    val tier: ProductGrade,
    val isDeleted: Boolean,
    @ReadOnly val createdAt: LocalDateTime,
    @ReadOnly val updatedAt: LocalDateTime,
    )