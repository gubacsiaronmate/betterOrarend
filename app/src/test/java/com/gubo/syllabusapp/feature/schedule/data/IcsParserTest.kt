package com.gubo.syllabusapp.feature.schedule.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId


class IcsParserTest {
    private val parser = IcsParser()

    @Test
    fun `parse returns empty list when input is empty`() {
        val result = parser.parse("")
        assertEquals(0, result.size)
    }

    @Test
    fun `parse returns one session for a single VEVENT`() {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc123
            DTSTART:20260223T080000Z
            DTEND:20260223T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        val result = parser.parse(icsContent)

        assertEquals(1, result.size)
    }

    @Test
    fun `parse extracts subject from SUMMARY`() {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc123
            DTSTART:20260223T080000Z
            DTEND:20260223T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        val result = parser.parse(icsContent)

        assertEquals("Gyógypedagógiai szociológia", result[0].subject)
        assertEquals("Dr. Czövek Andrea", result[0].instructor)
        assertEquals("ÉK.Als.3", result[0].location)
    }

    @Test
    fun `parse extracts correct start and end times`() {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc123
            DTSTART:20260223T080000Z
            DTEND:20260223T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        val result = parser.parse(icsContent)

        val zone = ZoneId.of("Europe/Budapest")
        assertEquals(9, result[0].startTime.withZoneSameInstant(zone).hour)
        assertEquals(0, result[0].startTime.withZoneSameInstant(zone).minute)
        assertEquals(10, result[0].endTime.withZoneSameInstant(zone).hour)
        assertEquals(40, result[0].endTime.withZoneSameInstant(zone).minute)
    }
}