package com.gubo.syllabusapp.feature.schedule.domain.model

import java.time.ZonedDateTime

data class UserEvent(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val location: String?,
    val startTime: ZonedDateTime,
    val endTime: ZonedDateTime
)
