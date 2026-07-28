package dev.shaper.akdymall.features.common.database

import org.jetbrains.exposed.v1.core.Column
import java.util.concurrent.ConcurrentHashMap

object TimestampTableUtils {

    private val columnCache = ConcurrentHashMap<Pair<TimestampTable, String>, Column<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> TimestampTable.cached(name: String, factory: () -> Column<T>): Column<T> =
        columnCache.computeIfAbsent(this to name) { factory() } as Column<T>

}