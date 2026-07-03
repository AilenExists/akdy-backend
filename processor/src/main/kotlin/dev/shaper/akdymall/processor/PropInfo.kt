package dev.shaper.akdymall.processor

data class PropInfo(
    val name: String,
    val nullable: Boolean,
    val isId: Boolean,
    val assignId: Boolean,   // @Id(assign=true)
    val isReference: Boolean,
    val isReadOnly: Boolean,
    val column: String,      // 최종 컬럼명 (id면 무시하고 "id" 사용)
)