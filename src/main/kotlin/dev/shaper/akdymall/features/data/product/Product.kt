package dev.shaper.akdymall.features.data.product

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Product (
    val productId: Long,    //PK
    val categoryId: Long?,  //FK
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
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    )