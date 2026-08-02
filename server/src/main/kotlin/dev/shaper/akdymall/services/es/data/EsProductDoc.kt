package dev.shaper.akdymall.services.es.data

data class EsProductDoc(
    val id: Long,
    val categoryId: Long,
    val categoryName: String,
    val name: String,
    val description: String,
    val tags: List<String>,       // 검색·필터용 — 지금 빠져있는 게 이상함
    val thumbnail: String,        // images[0]
    val badges: List<String>,
    val certQuote: String?,
    val sellerName: String,
    val location: String,
    val price: Long,
    val salePrice: Long,          // 정렬·필터에도 필요
    val discountRate: Int,
    val rating: Double,           // ratingSum / ratingCount, 색인 시 계산
    val quantitySum: Long,
    val viewCount: Long,
    val createdAt: String
)