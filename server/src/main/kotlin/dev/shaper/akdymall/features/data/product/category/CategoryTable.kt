package dev.shaper.akdymall.features.data.product.category

import dev.shaper.akdymall.features.common.database.BaseTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object CategoryTable : LongIdTable("product_categories"), BaseTable {
    val parentId = reference("parent_id", CategoryTable).nullable()
    val name = varchar("name", 255)
    val depth = short("depth")
    val sortOrder = integer("sort_order")
    val isActive = bool("is_active")
}