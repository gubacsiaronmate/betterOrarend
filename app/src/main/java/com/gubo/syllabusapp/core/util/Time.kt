package com.gubo.syllabusapp.core.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun ZonedDateTime.asFormattedStr(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}

fun Long.toZonedDateTime(zoneId: ZoneId): ZonedDateTime =
    Instant.ofEpochMilli(this).atZone(zoneId)

fun ZonedDateTime.toEpochMilli(): Long =
    this.toInstant().toEpochMilli()

val ZONE: ZoneId = ZoneId.of("Europe/Budapest")