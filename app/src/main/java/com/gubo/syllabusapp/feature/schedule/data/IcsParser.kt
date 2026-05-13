package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class IcsParser @Inject constructor() {
    fun parse(content: String): List<ClassSession> {
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