package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.data.manage.sync.SyncService
import dev.shaper.akdymall.features.data.product.option.OptionTable
import dev.shaper.akdymall.features.data.review.Review
import dev.shaper.akdymall.utils.ValueUtils.getCurrent
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.CaseWhen
import org.jetbrains.exposed.v1.core.Coalesce
import org.jetbrains.exposed.v1.core.doubleLiteral
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.greaterEq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.intLiteral
import org.jetbrains.exposed.v1.core.plus
import org.jetbrains.exposed.v1.core.sum
import org.jetbrains.exposed.v1.core.wrapAsExpression
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService: KoinComponent {

    val syncService: SyncService by inject()

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
            it[updatedAt] = getCurrent()
        }
        updated > 0
    }

    fun deleteProduct(productId: Long): Boolean = transaction {
        val updated = ProductTable.update({ ProductTable.id eq productId }) {
            it[isDeleted] = true
            it[updatedAt] = getCurrent()
        }
        updated > 0
    }

    fun getProductSince(time: LocalDateTime): List<Product> = transaction {
        ProductTable.selectAll()
            .where { ProductTable.updatedAt greaterEq time }
            .toList()
            .map { it.toProduct() }
    }

    fun syncQuantity(productId: Long): Boolean = transaction {
        val sumSubquery = wrapAsExpression<Long>(
            OptionTable.select(Coalesce(OptionTable.quantity.sum(), intLiteral(0)))
                .where { OptionTable.productId eq ProductTable.id }
        )
        val updated = ProductTable.update({ ProductTable.id eq productId }) {
            it[quantitySum] = sumSubquery
        }
        updated > 0
    }

    fun syncQuantity(productIds: Collection<Long>): Boolean = transaction {
        if (productIds.isEmpty()) return@transaction false

        val sumSubquery = wrapAsExpression<Long>(
            OptionTable.select(Coalesce(OptionTable.quantity.sum(), intLiteral(0)))
                .where { OptionTable.productId eq ProductTable.id }
        )
        ProductTable.update({ ProductTable.id inList productIds }) {
            it[quantitySum] = sumSubquery
        } > 0
    }

    fun syncQuantityAll() : Boolean = transaction {
        val sumSubquery = wrapAsExpression<Long>(
            OptionTable.select(Coalesce(OptionTable.quantity.sum(), intLiteral(0)))
                .where { OptionTable.productId eq ProductTable.id }
        )

        val updated = ProductTable.update {
            it[quantitySum] = sumSubquery
        }
        updated > 0
    }


    fun applyReview(review: Review) = transaction {
        ProductTable.update({ ProductTable.id eq review.productId }) {
            it[ratingSum]   = ratingSum + (review.rating/2).toDouble()
            it[ratingCount] = ratingCount + 1
        }
    }

    fun applyReview(reviews: Collection<Review>): Int = transaction {
        if (reviews.isEmpty()) return@transaction 0

        val deltas = reviews
            .groupBy({ it.productId }, { it.rating })
            .mapValues { (_, ratings) -> ratings.sumOf { (it / 2).toDouble() } to ratings.size }

        var sumCase = CaseWhen<Double>()
        var countCase = CaseWhen<Int>()
        deltas.forEach { (productId, delta) ->
            val cond = ProductTable.id eq productId
            sumCase = sumCase.When(cond, doubleLiteral(delta.first))
            countCase = countCase.When(cond, intLiteral(delta.second))
        }

        ProductTable.update({ ProductTable.id inList deltas.keys }) {
            it[ProductTable.ratingSum]   = ProductTable.ratingSum + sumCase.Else(doubleLiteral(0.0))
            it[ProductTable.ratingCount] = ProductTable.ratingCount + countCase.Else(intLiteral(0))
        }
    }



}