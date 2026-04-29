package com.gubo.syllabusapp.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun <T : Any> KClass<T>.instantiate(): T? {
    this.objectInstance?.let { return it }

    val ctor = this.primaryConstructor ?: return null

    val args = ctor.parameters.associateWith { param ->
        val classifier = param.type.classifier as? KClass<*> ?: return null
        classifier.constructValue()
    }

    return runCatching { ctor.callBy(args) }.getOrNull()
}

fun KClass<*>.constructValue(): Any? {
    return when (this) {
        Int::class -> 0
        String::class -> ""
        Boolean::class -> false
        Float::class -> 0f
        Double::class -> 0.0
        Long::class -> 0L
        Short::class -> 0.toShort()
        Byte::class -> 0.toByte()
        Char::class -> ' '
        List::class -> emptyList<Any>()
        Map::class -> emptyMap<Any, Any>()
        Set::class -> emptySet<Any>()
        ZonedDateTime::class -> ZonedDateTime.now()
        LocalDateTime::class -> LocalDateTime.now()
        LocalDate::class -> LocalDate.now()
        LocalTime::class -> LocalTime.now()
        Instant::class -> Instant.now()
        ZoneId::class -> ZoneId.systemDefault()
        ZoneOffset::class -> ZonedDateTime.now().offset
        OffsetDateTime::class -> OffsetDateTime.now()
        OffsetTime::class -> OffsetTime.now()
        else -> instantiate() // recurse
    }
}