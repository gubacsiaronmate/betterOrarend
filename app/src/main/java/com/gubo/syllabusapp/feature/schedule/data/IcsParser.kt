package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import java.time.ZonedDateTime

class IcsParser {
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
        
        return ClassSession(
            subject = subject,
            instructor = instructor,
            location = location,
            startTime = placeholder,
            endTime = placeholder
        )
    }
}