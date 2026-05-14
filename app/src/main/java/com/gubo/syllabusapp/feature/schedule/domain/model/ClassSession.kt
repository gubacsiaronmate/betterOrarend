package com.gubo.syllabusapp.feature.schedule.domain.model

import java.time.ZonedDateTime

data class ClassSession(
    override val title: String,
    override val location: String,
    override val startTime: ZonedDateTime,
    override val endTime: ZonedDateTime,

    val instructor: String,
) : Displayable