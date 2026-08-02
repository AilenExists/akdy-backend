package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.data.product.category.CategoryTable
import dev.shaper.akdymall.features.common.database.TimestampTable
import dev.shaper.akdymall.features.data.user.UserTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object ProductTable: LongIdTable("product"), TimestampTable {
    val categoryId = reference("category_id", CategoryTable).nullable()
    val tags = json<JsonElement>("tags", Json)
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val badges = array<String>("badges")
    val certQuote = enumeration<ProductCert>("cert_quote")
    val sellerName = varchar("seller_name", 255)
    val sellerId = reference("seller_id", UserTable)
    val price = long("price")
    val salePrice = long("sale_price")
    val discountRate = integer("discount_rate")
    val buyMin = integer("buy_min")
    val buyMax = integer("buy_max")
    val rating = decimal("rating",2,1).databaseGenerated()
    val ratingSum = double("rating_sum")
    val ratingCount = integer("rating_count")
    val quantitySum = long("quantity_sum")
    val viewCount = long("view_count")
    val images = array<String>("images")
    val location = varchar("location", 255)
    val tier = enumeration<ProductGrade>("tier")
    val isDeleted = bool("is_deleted")
}