package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import java.time.ZonedDateTime

class IcsParser {
    fun parse(content: String): List<ClassSession> {
        if (content.isBlank()) return emptyList()

        val events = mutableListOf<ClassSession>()
        var inEvent = false
        val placeholder = ZonedDateTime.now()

        content.lines().forEach { line ->
            when (line.trim()) {
                "BEGIN:VEVENT" -> inEvent = true
                "END:VEVENT" -> {
                    if (inEvent) events.add(ClassSession("", "", "", placeholder, placeholder))
                    inEvent = false
                }
            }
        }

        return events
    }
}