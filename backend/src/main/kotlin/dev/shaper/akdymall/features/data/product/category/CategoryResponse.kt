package dev.shaper.akdymall.features.data.product.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Long,
    val name: String,
)
