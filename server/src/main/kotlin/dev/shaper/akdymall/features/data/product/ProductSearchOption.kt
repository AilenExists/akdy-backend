package dev.shaper.akdymall.features.data.product

data class ProductSearchOption(
    val keyword: String,
    val count: Int,
    val categoryId: Long?,
    val page: Int,
    val sort: ProductSortOption,
    val priceRange: Pair<Int,Int>
)
