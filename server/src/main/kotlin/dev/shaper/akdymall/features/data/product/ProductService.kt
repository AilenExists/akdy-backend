package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.utils.ValueUtils.nowTime
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class ProductService {

    fun createProduct(product: Product): Product = transaction {
        val id = ProductTable.insertAndGetId {
            it.fromProduct(product)
        }
        ProductTable.selectAll()
            .where { ProductTable.id eq id }
            .single()
            .toProduct()
    }

    fun readProductById(productId: Long): Product? = transaction {
        ProductTable.selectAll()
            .where { ProductTable.id eq productId }
            .singleOrNull()
            ?.toProduct()
    }

//    fun findProductByOption(option: ProductSearchOption): List<Product> = transaction {
//        var exp: Op<Boolean> =  ProductTable.name like "%${option.categoryId}%"
//        if(option.categoryId != null)
//            exp = exp and (ProductTable.categoryId eq option.categoryId)
//        val sort = when(option.sort) {
//
//        }
//        ProductTable.selectAll()
//            .where { exp }
//    }

    fun updateProduct(product: Product): Boolean = transaction {
        val updated = ProductTable.update({ ProductTable.id eq product.productId }) {
            it.fromProduct(product)
            it[updatedAt] = nowTime()
        }
        updated > 0
    }

    fun deleteProduct(productId: Long): Boolean = transaction {
        val updated = ProductTable.update({ ProductTable.id eq productId }) {
            it[isDeleted] = true
            it[updatedAt] = nowTime()
        }
        updated > 0
    }
}