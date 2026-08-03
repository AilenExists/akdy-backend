package dev.shaper.akdymall.services.settings

import io.github.oshai.kotlinlogging.KLogger
import kotlinx.coroutines.CoroutineExceptionHandler

class Settings(val logger: KLogger) {

    fun printException(thread: Thread?,exception: Throwable) {
        logger.error { "Uncaught exception ${if(thread != null) "in thread" else ""} ${thread?.name}: ${exception.message}" }
        exception.stackTrace.forEach {
            logger.error { "\t\t$it" }
        }
    }

    fun errorHandler(){
        Thread.setDefaultUncaughtExceptionHandler { thread, exception -> printException(thread,exception) }
        CoroutineExceptionHandler { _, exception -> printException(null,exception) }
    }

}