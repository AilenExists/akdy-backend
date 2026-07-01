package dev.shaper.akdymall.annotations

// 이 프로퍼티가 테이블 id 에 매핑. assign=true면 from()에도 포함(UUID 등 앱 생성 id)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class Id(val assign: Boolean = false)