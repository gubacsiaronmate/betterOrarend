package com.gubo.syllabusapp.feature.schedule.domain.repository

import app.cash.turbine.test
import com.gubo.syllabusapp.core.util.MainDispatcherRule
import com.gubo.syllabusapp.core.util.ZONE
import com.gubo.syllabusapp.feature.schedule.data.FakeClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.FakeSemesterDao
import com.gubo.syllabusapp.feature.schedule.data.FakeUserEventDao
import com.gubo.syllabusapp.feature.schedule.data.IcsParser
import com.gubo.syllabusapp.feature.schedule.data.ScheduleRepositoryImpl
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventDao
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.ZonedDateTime

class ScheduleRepositoryTest {
    private lateinit var semesterDao: SemesterDao
    private lateinit var classSessionDao: ClassSessionDao
    private lateinit var repository: ScheduleRepositoryImpl
    private lateinit var userEventDao: UserEventDao

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        semesterDao = FakeSemesterDao()
        classSessionDao = FakeClassSessionDao()
        userEventDao = FakeUserEventDao()
        repository = ScheduleRepositoryImpl(
            semesterDao = semesterDao,
            classSessionDao = classSessionDao,
            userEventDao = userEventDao,
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

    @Test
    fun `importFromIcs parses class sessions correctly`() = runTest {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc001
            DTSTART:20260224T080000Z
            DTEND:20260224T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        repository.importFromIcs(icsContent, "2025/26 tavasz")

        repository.getSessionsForWeek(LocalDate.of(2026, 2, 23)).test {
            val sessions = awaitItem()
            assertEquals(1, sessions.size)
            assertEquals("Gyógypedagógiai szociológia", sessions.first().subject)
            assertEquals("ÉK.Als.3", sessions.first().location)
            assert(LocalDate.of(2026, 2, 23) < sessions.first().startTime.toLocalDate())
            assert(LocalDate.of(2026, 3, 1) > sessions.first().startTime.toLocalDate())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUserEventsForWeek returns correct data for given input`() = runTest {
        val icsContent = """
            BEGIN:VCALENDAR
            BEGIN:VEVENT
            UID:abc001
            DTSTART:20260224T080000Z
            DTEND:20260224T094000Z
            SUMMARY:Gyógypedagógiai szociológia ( - EC-I-2-1 csoport) - Dr. Czövek Andrea - Tanóra
            LOCATION:ÉK.Als.3 (E.ÉK.Als.3)
            END:VEVENT
            END:VCALENDAR
        """.trimIndent()

        repository.importFromIcs(icsContent, "2025/26 tavasz")

        val event = UserEvent(
            title = "Pszicho zh",
            description = null,
            location = "ersekkert",
            startTime = ZonedDateTime.of(2026, 4, 1, 12, 0, 0, 0, ZONE),
            endTime = ZonedDateTime.of(2026, 4, 1, 14, 30, 0, 0, ZONE)
        )

        repository.addUserEvent(event)

        repository.getUserEventsForWeek(LocalDate.of(2026, 3, 30)).test {
            val events = awaitItem()
            assertEquals(1, events.size)
            assertEquals(event.title, events.first().title)
            assertEquals(event.description, events.first().description)
            assertEquals(event.location, events.first().location)
            assert(LocalDate.of(2026, 3, 30) < events.first().startTime.toLocalDate())
            assert(LocalDate.of(2026, 4, 5) > events.first().startTime.toLocalDate())
            cancelAndIgnoreRemainingEvents()
        }
    }
}