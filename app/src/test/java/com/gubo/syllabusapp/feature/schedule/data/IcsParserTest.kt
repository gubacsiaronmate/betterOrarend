package com.gubo.syllabusapp.feature.schedule.data

import org.junit.Assert.assertEquals
import org.junit.Test


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
}