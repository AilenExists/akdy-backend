package dev.shaper.akdymall.features.data.product.category

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Long, //PK
    val parentId: Long?,
    val name: String,
    val depth: Short,
    val sortOrder: Int,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
