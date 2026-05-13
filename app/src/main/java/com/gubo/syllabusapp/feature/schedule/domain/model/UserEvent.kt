package com.gubo.syllabusapp.feature.schedule.domain.model

import java.time.ZonedDateTime

data class UserEvent(
    val id: Long = 0,

    override val title: String,
    override val location: String?,
    override val startTime: ZonedDateTime,
    override val endTime: ZonedDateTime,

    val description: String?,
) : Displayable
