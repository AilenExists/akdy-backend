package dev.shaper.akdymall.utils

import org.jetbrains.exposed.v1.dao.Entity
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
object ExposedUtils {
    fun <DTO : Any, ENTITY : Entity<*>> ENTITY.copyFrom(
        dto: DTO,
        override: (ENTITY.() -> Unit)? = null
    ): ENTITY {
        val dtoProps = dto::class.memberProperties.associateBy { it.name }
        this::class.memberProperties.forEach { entProp ->
            if (entProp is KMutableProperty1<*, *> && entProp.name != "id") {
                dtoProps[entProp.name]?.let { dtoProp ->
                    val value = dtoProp.getter.call(dto)

                    if (value != null && entProp.returnType == dtoProp.returnType) {
                        @Suppress("UNCHECKED_CAST")
                        (entProp as KMutableProperty1<Any, Any?>).set(this, value)
                    }
                }
            }
        }
        override?.invoke(this)
        return this
    }
}