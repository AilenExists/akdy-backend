package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.data.product.category.CategoryEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.json.json

class ProductEntity(id : EntityID<Long>): LongEntity(id) {

    object ProductTable: LongIdTable("product") {
        val categoryId = reference("category_id", CategoryEntity.CategoryTable)
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
    companion object : LongEntityClass<ProductEntity>(ProductTable)
    var categoryId by ProductTable.categoryId
    var tags by ProductTable.tags
    var name by ProductTable.name
    var description by ProductTable.description
    var regularPrice by ProductTable.regularPrice
    var salePrice by ProductTable.salePrice
    var buyMin by ProductTable.buyMin
    var buyMax by ProductTable.buyMax
    var images by ProductTable.images
    var manufacture by ProductTable.manufacture
    var tier by ProductTable.tier
    var isDeleted by ProductTable.isDeleted
    var createdAt by ProductTable.createdAt
    var updatedAt by ProductTable.updatedAt
    
    fun toProduct() = Product(
        id.value,
        categoryId.value,
        tags,
        name,
        description,
        regularPrice,
        salePrice,
        buyMin,
        buyMax,
        images,
        manufacture,
        tier,
        isDeleted,
        createdAt,
        updatedAt
    )
    
}