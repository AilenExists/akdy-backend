package dev.shaper.akdymall.features.data.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: Long,
    val thumbnail: String,
    val badges: List<String>,
    val title: String,
    val seller: String,
    val location: String,
    val certificationQuote: String, //뭐임 이건 설명필요
    val rating: Double,
    val reviewCount: Int,
    val originalPrice: Int,
    val salePrice: Int,
    //liked -> 너무 오버해드임 하고싶으면 프론트단에서 처리 -> 백
)
