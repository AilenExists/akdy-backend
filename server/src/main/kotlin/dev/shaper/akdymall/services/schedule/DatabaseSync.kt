package dev.shaper.akdymall.services.schedule

import com.github.kagkarlsson.scheduler.task.helper.RecurringTask
import com.github.kagkarlsson.scheduler.task.helper.Tasks
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay
import com.github.kagkarlsson.scheduler.task.schedule.Schedules
import dev.shaper.akdymall.features.data.manage.sync.SyncService
import dev.shaper.akdymall.features.data.product.ProductService
import dev.shaper.akdymall.features.data.review.ReviewService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatabaseSync: KoinComponent {

    private val productService: ProductService by inject()
    private val reviewService: ReviewService by inject()
    private val syncService: SyncService by inject()

    fun productSyncTask(): RecurringTask<Void> = Tasks.recurring("product-sync", FixedDelay.ofMinutes(5))
        .execute { instance, context ->
            val lastSync = syncService.getLastProductSync() ?: throw IllegalStateException()
            val products = productService.getProductSince(lastSync)
            val updated  = productService.syncQuantity(products.map { it.productId })
            syncService.syncProduct()
            // 추후 selectAll 이 아닌 특정 열만 가져오도록 수정
        }

    fun ratingSyncTask(): RecurringTask<Void> = Tasks.recurring("rating-sync", Schedules.cron("0 0 4 * * ?"))
        .execute { instance, context ->
            val lastSync = syncService.getLastRatingSync() ?: throw IllegalStateException()
            val reviews = reviewService.getReviewSince(lastSync)
            val updated = productService.applyReview(reviews)
            syncService.syncRating()
            // 로그 시스템 기록
        }

    fun elasticSearchSyncTask(): RecurringTask<Void> = Tasks.recurring("elasticsearch-sync", FixedDelay.ofMinutes(5))
        .execute { instance, context -> // pgsync 도입으로 해소?됨
        }

    //조회수 sync 5분 주기 추가
}