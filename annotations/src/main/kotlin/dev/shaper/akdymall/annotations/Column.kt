package dev.shaper.akdymall.annotations

// 컬럼명이 프로퍼티명과 다를 때 override
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class Column(val name: String)