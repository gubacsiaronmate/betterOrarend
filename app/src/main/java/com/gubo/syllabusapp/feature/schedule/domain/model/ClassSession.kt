package com.gubo.syllabusapp.feature.schedule.domain.model

import java.time.ZonedDateTime

data class ClassSession(
    val subject: String,
    val instructor: String,
    val location: String,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime
)