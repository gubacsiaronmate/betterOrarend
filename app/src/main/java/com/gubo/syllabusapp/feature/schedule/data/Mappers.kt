package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.core.util.toEpochMilli
import com.gubo.syllabusapp.core.util.toZonedDateTime
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionEntity
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterEntity
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventEntity
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import java.time.ZoneId

private val zone = ZoneId.of("Europe/Budapest")

fun ClassSessionEntity.toDomain(): ClassSession = ClassSession(
    subject = subject,
    instructor = instructor,
    location = location,
    startTime = startTimeUtc.toZonedDateTime(zone),
    endTime = endTimeUtc.toZonedDateTime(zone)
)

fun ClassSession.toEntity(semesterId: Long, uid: String): ClassSessionEntity = ClassSessionEntity(
    uid = uid,
    semesterId = semesterId,
    subject = subject,
    instructor = instructor,
    location = location,
    startTimeUtc = startTime.toEpochMilli(),
    endTimeUtc = endTime.toEpochMilli()
)

fun SemesterEntity.toDomain(): Semester = Semester(
    id = id,
    name = name,
    importedAt = importedAt,
    isActive = isActive
)

fun UserEventEntity.toDomain(): UserEvent = UserEvent(
    id = id,
    title = title,
    description = description,
    location = location,
    startTime = startTimeUtc.toZonedDateTime(zone),
    endTime = endTimeUtc.toZonedDateTime(zone)
)

fun UserEvent.toEntity(semesterId: Long): UserEventEntity = UserEventEntity(
    id = id,
    semesterId = semesterId,
    title = title,
    description = description,
    location = location,
    startTimeUtc = startTime.toEpochMilli(),
    endTimeUtc = startTime.toEpochMilli()
)