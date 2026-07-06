package dev.shaper.akdymall.features.data.product.category

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

class CategoryEntity(id: EntityID<Long>) : LongEntity(id) {

    object CategoryTable : LongIdTable("product_categories") {
        val parentId = reference("parent_id", CategoryTable).nullable()
        val name = varchar("name", 255)
        val depth = short("depth")
        val sortOrder = integer("sort_order")
        val isActive = bool("is_active")
        val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
        val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
    }
    
    companion object : LongEntityClass<CategoryEntity>(CategoryTable)
    var parentId by CategoryTable.parentId
    var name by CategoryTable.name
    var depth by CategoryTable.depth
    var sortOrder by CategoryTable.sortOrder
    var isActive by CategoryTable.isActive
    var createdAt by CategoryTable.createdAt
    var updatedAt by CategoryTable.updatedAt
    
    fun toCategory() = Category(
        id.value,
        parentId?.value,
        name,
        depth,
        sortOrder,
        isActive,
        createdAt,
        updatedAt
    )
}