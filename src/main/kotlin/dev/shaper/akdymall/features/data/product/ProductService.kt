package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.utils.ExposedUtils.copyFrom
import dev.shaper.akdymall.utils.ValueUtils.nowTime
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID

class ProductService {

    fun createProduct(product: Product): Product = transaction {
        ProductEntity.new { copyFrom(product) }.toProduct()
    }

    fun readProductById(productId: Long) : Product? = transaction {
        ProductEntity.findById(productId)?.toProduct()
    }

    fun updateProduct(product: Product): Boolean = transaction {
        val entity = ProductEntity.findById(product.productId) ?: return@transaction false
        entity.copyFrom(product) { this.updatedAt = nowTime()}
        true
    }

    fun deleteProduct(productId: Long) = transaction {
        val entity = ProductEntity.findById(productId) ?: return@transaction false
        entity.isDeleted = true
        entity.updatedAt = nowTime()
        true
    }


}