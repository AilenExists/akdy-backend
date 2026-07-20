package dev.shaper.akdymall.features.data.product.category

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class CategoryService {

    fun createCategory(category: Category): Category = transaction {
        val id = CategoryTable.insertAndGetId { it.fromCategory(category) }
        CategoryTable.selectAll()
            .where { CategoryTable.id eq id}
            .single()
            .toCategory()
    }

    fun readCategoryCount(count: Int): List<Category> = transaction {
        CategoryTable.selectAll()
            .limit(count)
            .toList()
            .map { it.toCategory() }
    }

}