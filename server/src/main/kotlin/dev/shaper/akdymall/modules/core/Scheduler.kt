package dev.shaper.akdymall.modules.core

import com.github.kagkarlsson.scheduler.Scheduler
import dev.shaper.akdymall.services.schedule.DatabaseSync
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject
import javax.sql.DataSource

fun Application.configureScheduler() {
    val dataSource: DataSource by inject()
    val scheduler = Scheduler
        .create(
            dataSource,
            DatabaseSync.productSyncTask(),
            DatabaseSync.ratingSyncTask(),
            //DatabaseSync.elasticSearchSyncTask()
        )
        .threads(4)
        .registerShutdownHook()
        .build()
    scheduler.start()
}