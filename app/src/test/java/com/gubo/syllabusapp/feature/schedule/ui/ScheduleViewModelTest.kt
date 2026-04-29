package com.gubo.syllabusapp.feature.schedule.ui

import app.cash.turbine.test
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import com.gubo.syllabusapp.feature.schedule.domain.repository.FakeScheduleRepository
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class ScheduleViewModelTest {
    private lateinit var repository: ScheduleRepository
    private lateinit var viewModel: ScheduleViewModel

    @Before
    fun setup() {
        repository = FakeScheduleRepository()
        viewModel = ScheduleViewModel(repository)
    }

    @Test
    fun `viewmodel inital state`() = runTest {
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertNotEquals(uiState, null)
            assertEquals(uiState.isLoading, false)
            assertEquals(uiState.userEvents, emptyMap<DayOfWeek, UserEvent>())
            assertEquals(uiState.sessions, emptyMap<DayOfWeek, ClassSession>())
            assertEquals(uiState.currentWeekStart, LocalDate.now().with(DayOfWeek.MONDAY))
            assertEquals(uiState.activeSemester, null)
            cancelAndIgnoreRemainingEvents()
        }
    }
}