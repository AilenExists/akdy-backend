package dev.shaper.akdymall.modules.core

import com.github.kagkarlsson.scheduler.Scheduler
import dev.shaper.akdymall.services.schedule.DatabaseSync
import io.ktor.server.application.Application
import org.koin.ktor.ext.inject
import javax.sql.DataSource

fun Application.configureScheduler() {
    fun ensureSchedulerTable(dataSource: DataSource) {
        dataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("""
                CREATE TABLE IF NOT EXISTS scheduled_tasks (
                  task_name text not null,
                  task_instance text not null,
                  task_data bytea,
                  execution_time timestamp with time zone not null,
                  picked BOOLEAN not null,
                  picked_by text,
                  last_success timestamp with time zone,
                  last_failure timestamp with time zone,
                  consecutive_failures INT,
                  last_heartbeat timestamp with time zone,
                  version BIGINT not null,
                  priority SMALLINT,
                  PRIMARY KEY (task_name, task_instance)
                )
            """.trimIndent())
            }
        }
    }
    val dataSource: DataSource by inject()
    ensureSchedulerTable(dataSource)
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