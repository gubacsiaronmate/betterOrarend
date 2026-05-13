package com.gubo.syllabusapp.feature.schedule.domain.model

import java.time.ZonedDateTime

interface Displayable {
    val title: String
    val location: String?
    val startTime: ZonedDateTime
    val endTime: ZonedDateTime
}