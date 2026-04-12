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
}