package com.gubo.syllabusapp.feature.schedule.domain.repository

import app.cash.turbine.test
import com.gubo.syllabusapp.feature.schedule.data.FakeClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.FakeSemesterDao
import com.gubo.syllabusapp.feature.schedule.data.IcsParser
import com.gubo.syllabusapp.feature.schedule.data.ScheduleRepositoryImpl
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ScheduleRepositoryTest {
    private lateinit var semesterDao: SemesterDao
    private lateinit var classSessionDao: ClassSessionDao
    private lateinit var repository: ScheduleRepositoryImpl

    @Before
    fun setup() {
        semesterDao = FakeSemesterDao()
        classSessionDao = FakeClassSessionDao()
        repository = ScheduleRepositoryImpl(
            semesterDao = semesterDao,
            classSessionDao = classSessionDao,
            icsParser = IcsParser()
        )
    }

    @Test
    fun `importFromIcs creates a new active semester`() = runTest {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc001
            DTSTART:20260223T080000Z
            DTEND:20260223T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        repository.importFromIcs(icsContent, "2025/26 tavasz")

        repository.getAllSemesters().test {
            val semester = awaitItem()
            assertEquals(1, semester.size)
            assertEquals("2025/26 tavasz", semester.first().name)
            assertEquals(true, semester.first().isActive)
            cancelAndIgnoreRemainingEvents()
        }
    }
}