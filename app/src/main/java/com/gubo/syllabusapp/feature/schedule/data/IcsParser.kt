package com.gubo.syllabusapp.feature.schedule.data

import android.util.Log
import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.io.TimezoneInfo
import biweekly.io.text.ICalReader
import biweekly.property.DateOrDateTimeProperty
import com.gubo.syllabusapp.core.util.ZONE
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IcsParser @Inject constructor() {

    fun parse(content: String): List<ClassSession> {
        if (content.isBlank()) return emptyList()

        val ical = parseContentToObject(content)
        val tzInfo = ical.timezoneInfo

        return ical.events.toClassSessions(tzInfo)
    }

    private fun List<VEvent>.toClassSessions(tzInfo: TimezoneInfo): List<ClassSession> = map { it.toClassSession(tzInfo) }
    
    private fun VEvent.toClassSession(tzInfo: TimezoneInfo): ClassSession {
        val summary = this.summary?.value ?: ""
        val subject = summary.substringBefore("(").trim()
        val instructor = summary
            .substringAfter(")")
            .split("-")
            .map { it.trim() }
            .firstOrNull { it.isNotBlank() && it != "Tanóra" }
            ?: ""
        val location = this.location?.value?.substringBefore("(")?.trim() ?: ""

        return ClassSession(
            title = subject,
            location = location,
            startTime = this.dateStart.toZonedDateTime(tzInfo),
            endTime = this.dateEnd.toZonedDateTime(tzInfo),
            instructor = instructor
        )
    }

    private fun DateOrDateTimeProperty.toZonedDateTime(tzInfo: TimezoneInfo): ZonedDateTime {
        return when {
            !value.hasTime() ->
                value.rawComponents?.run {
                    Log.d("IcsParser", "toZonedDateTime: DateStart has no time component")
                    LocalDate.of(year, month, date).atStartOfDay(ZONE)
                } ?: error("DSTART has no raw components. Something ain't right with parsing.")

            tzInfo.isFloating(this) -> value.rawComponents
                .run { LocalDateTime.of(year, month, date, hour, minute, second).atZone(ZONE) }
                ?: error("DSTART has no raw components. Something ain't right with parsing.")

            else ->
                value.toInstant().atZone(tzInfo.getTimezone(this)?.timeZone?.toZoneId() ?: ZONE)
        }
    }

    private fun parseContentToObject(content: String): ICalendar {
        val reader = ICalReader(content)
        val cals = reader.readAll()
        return when (cals.size) {
            0 -> error("No calendars found!")
            1 -> cals.single()
            else -> cals.latest()
        }
    }

    private fun List<VEvent>.latest(): VEvent = maxBy { it.dateStart.value }

    private fun List<ICalendar>.latest(): ICalendar =
        first { it.events.contains(it.events.latest()) }

    @Deprecated(
        message = "The new parse(content: String) method does the parsing by using biweekly instead of my hand written code so its less fragile than this.",
        replaceWith = ReplaceWith("parse(content)"),
        level = DeprecationLevel.WARNING
    )
    fun parseByHand(content: String): List<ClassSession> {
        if (content.isBlank()) return emptyList()

        val events = mutableListOf<ClassSession>()
        var inEvent = false
        val currentEvent = mutableMapOf<String, String>()
        val placeholder = ZonedDateTime.now()

        content.lines().forEach { line ->
            val trimmed = line.trim()
            when {
                trimmed == "BEGIN:VEVENT" -> {
                    inEvent = true
                    currentEvent.clear()
                }
                trimmed == "END:VEVENT" -> {
                    if (inEvent)
                        events.add(currentEvent.toClassSession(placeholder))
                    inEvent = false
                }
                inEvent && trimmed.contains(":") -> {
                    val key = trimmed.substringBefore(":")
                    val value = trimmed.substringAfter(":")
                    currentEvent[key] = value
                }
            }
        }

        return events
    }

    private fun Map<String, String>.toClassSession(placeholder: ZonedDateTime): ClassSession {
        val summary = this["SUMMARY"] ?: ""
        val subject = summary.substringBefore("(").trim()
        val instructor = summary
            .substringAfter(")")
            .split("-")
            .map { it.trim() }
            .firstOrNull { it.isNotBlank() && it != "Tanóra" }
            ?: ""
        val location = this["LOCATION"]
            ?.substringBefore("(")
            ?.trim() ?: ""

        val startTime = this["DTSTART"]?.parseIcsDateTime() ?: placeholder
        val endTime = this["DTEND"]?.parseIcsDateTime() ?: placeholder

        return ClassSession(
            title = subject,
            instructor = instructor,
            location = location,
            startTime = startTime,
            endTime = endTime
        )
    }

    private fun String.parseIcsDateTime(): ZonedDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
        val localDateTime = LocalDateTime.parse(this, formatter)
        return localDateTime.atZone(ZoneOffset.UTC)
    }
}