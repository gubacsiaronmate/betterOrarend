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

        assertEquals("Gyógypedagógiai szociológia", result[0].title)
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

    @Test
    fun `parse returns all sessions from a multi event ICS`() {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc001
            DTSTART:20260223T080000Z
            DTEND:20260223T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            BEGIN:VEVENT
            UID:abc002
            DTSTART:20260224T085500Z
            DTEND:20260224T113000Z
            SUMMARY:Tipikus és atipikus fejlődés pszichológiája ( - EC-I-2-1 csoport) - Dr. Dávid Mária - Tanóra
            LOCATION:C.I.119 (E.C.I.119)
            END:VEVENT
            BEGIN:VEVENT
            UID:abc003
            DTSTART:20260225T060000Z
            DTEND:20260225T074000Z
            SUMMARY:Gyógypedagógiai fejlődéstan ( - EC-I-1) - Dr. Fodó Eszter - Tanóra
            LOCATION:ÉK.Als.2 (E.ÉK.Als.2)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        val result = parser.parse(icsContent)

        assertEquals(3, result.size)
        assertEquals("Gyógypedagógiai szociológia", result[0].title)
        assertEquals("Tipikus és atipikus fejlődés pszichológiája", result[1].title)
        assertEquals("Gyógypedagógiai fejlődéstan", result[2].title)
    }
}