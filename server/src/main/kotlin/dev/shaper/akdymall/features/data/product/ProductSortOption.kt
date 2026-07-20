package dev.shaper.akdymall.features.data.product

import org.jetbrains.exposed.v1.core.SortOrder

enum class ProductSortOption(
    val field: String,
    val direction: SortOrder,
    val description: String
) {
    RECOMMENDED("recommendScore", SortOrder.DESC, "추천순"),
    VIEW_COUNT_DESC("viewCount", SortOrder.DESC, "조회수 높은순"),
    VIEW_COUNT_ASC("viewCount", SortOrder.ASC, "조회수 낮은순"),
    NAME_ASC("name", SortOrder.ASC, "상품명 오름차순 (가나다순)"),
    NAME_DESC("name", SortOrder.DESC, "상품명 내림차순"),
    PRICE_ASC("price", SortOrder.ASC, "가격 낮은순"),
    PRICE_DESC("price", SortOrder.DESC, "가격 높은순"),
    CREATED_AT_DESC("createdAt", SortOrder.DESC, "최신순"),
    CREATED_AT_ASC("createdAt", SortOrder.ASC, "오래된순");

    companion object {
        // API 파라미터로 받은 문자열을 enum으로 변환 (대소문자 무시)
        fun from(value: String?): ProductSortOption =
            entries.find { it.name.equals(value, ignoreCase = true) }
                ?: CREATED_AT_DESC  // 기본값: 최신순
    }
}
