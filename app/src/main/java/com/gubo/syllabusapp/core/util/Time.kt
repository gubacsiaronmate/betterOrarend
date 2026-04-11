package com.gubo.syllabusapp.core.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun ZonedDateTime.asFormattedStr(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}