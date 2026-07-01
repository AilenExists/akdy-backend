package dev.shaper.akdymall.features.data.product.category

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object CategoryTable : LongIdTable("product_categories") {
    val parentId = reference("parent_id", CategoryTable).nullable()
    val name = varchar("name", 255)
    val depth = short("depth")
    val sortOrder = integer("sort_order")
    val isActive = bool("is_active")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}