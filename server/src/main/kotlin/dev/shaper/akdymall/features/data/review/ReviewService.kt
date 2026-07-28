package dev.shaper.akdymall.features.data.review

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.data.product.ProductTable.isDeleted
import dev.shaper.akdymall.utils.ValueUtils.getCurrent
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.greaterEq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class ReviewService {

    fun createReview(review: Review): Review = transaction {
        val id = ReviewTable.insertAndGetId { it.fromReview(review) }
        ReviewTable.selectAll()
            .where { ReviewTable.id eq id }
            .single()
            .toReview()
    }

    fun updateReview(review: Review): Boolean = transaction {
        val updated = ReviewTable.update ({ReviewTable.id eq review.reviewId}){
            it.fromReview(review)
            it[updatedAt] = getCurrent()
        }
        updated > 0
    }

    fun deleteReview(reviewId: Long) : Boolean = transaction {
        val updated = ReviewTable.update({ ReviewTable.id eq reviewId }) {
            it[isDeleted] = true
            it[updatedAt] = getCurrent()
        }
        updated > 0
    }

    fun getReviewSince(time: LocalDateTime) : List<Review> = transaction {
        ReviewTable.selectAll()
            .where { ReviewTable.updatedAt greaterEq time}
            .toList()
            .map { it.toReview() }
    }

}
