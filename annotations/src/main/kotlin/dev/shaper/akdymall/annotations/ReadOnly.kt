package dev.shaper.akdymall.annotations

// from() 에서 제외 (defaultExpression 컬럼 등)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class ReadOnly