package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.data.product.category.CategoryTable
import dev.shaper.akdymall.features.common.database.BaseTable
import dev.shaper.akdymall.features.data.user.UserTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object ProductTable: LongIdTable("product"), BaseTable {
    val categoryId = reference("category_id", CategoryTable).nullable()
    val tags = json<JsonElement>("tags", Json)
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val badges = array<String>("badges")
    val certQuote = enumeration<ProductCert>("cert_quote")
    val sellerName = varchar("seller_name", 255)
    val sellerId = reference("seller_id", UserTable)
    val regularPrice = double("regular_price")
    val salePrice = double("sale_price")
    val buyMin = integer("buy_min")
    val buyMax = integer("buy_max")
    val ratingSum = double("rating_sum")
    val ratingCount = integer("rating_count")
    val images = array<String>("images")
    val location = varchar("location", 255)
    val tier = enumeration<ProductGrade>("tier")
    val isDeleted = bool("is_deleted")
}