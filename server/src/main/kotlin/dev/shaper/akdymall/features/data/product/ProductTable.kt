package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.data.product.category.CategoryTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.json.json

object ProductTable: LongIdTable("product") {
    val categoryId = reference("category_id", CategoryTable)
    val tags = json<JsonElement>("tags", Json)
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val regularPrice = double("regular_price")
    val salePrice = double("sale_price")
    val buyMin = integer("buy_min")
    val buyMax = integer("buy_max")
    val images = array<String>("images")
    val manufacture = varchar("manufacture", 255)
    val tier = enumeration<ProductGrade>("tier")
    val isDeleted = bool("is_deleted")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)

}