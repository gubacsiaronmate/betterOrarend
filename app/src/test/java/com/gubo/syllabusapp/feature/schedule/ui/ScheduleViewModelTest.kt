package com.gubo.syllabusapp.feature.schedule.ui

import app.cash.turbine.test
import com.gubo.syllabusapp.core.util.MainDispatcherRule
import com.gubo.syllabusapp.core.util.instantiate
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import com.gubo.syllabusapp.feature.schedule.domain.repository.FakeScheduleRepository
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class ScheduleViewModelTest {
    private lateinit var repository: ScheduleRepository
    private lateinit var viewModel: ScheduleViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
            @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
            assertEquals(uiState!!.isLoading, false)
            assertEquals(uiState.sessions, emptyMap<DayOfWeek, List<ClassSession>>())
            assertEquals(uiState.userEvents, emptyMap<DayOfWeek, List<UserEvent>>())
            assertEquals(uiState.currentWeekStart, LocalDate.now().with(DayOfWeek.MONDAY))
            assertEquals(uiState.activeSemester, null)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `viewmodel actions work correctly`() = runTest {
        val actions = ScheduleAction::class.sealedSubclasses.mapNotNull { it.instantiate() }
        viewModel.uiState.test {
            var uiState = awaitItem()
            var weekStart = uiState.currentWeekStart
            actions.forEach { action ->
                when (action) {
                    ScheduleAction.NextWeek -> {
                        viewModel.onAction(action)
                        uiState = awaitItem()
                        assertEquals(weekStart.plusWeeks(1), uiState.currentWeekStart)
                        weekStart = uiState.currentWeekStart
                    }
                    ScheduleAction.PreviousWeek -> {
                        viewModel.onAction(action)
                        uiState = awaitItem()
                        assertEquals(weekStart.minusWeeks(1), uiState.currentWeekStart)
                        weekStart = uiState.currentWeekStart
                    }
                    is ScheduleAction.AddUserEvent -> {
                        val eventListSizeBefore = uiState.userEvents.size
                        viewModel.onAction(action)
                        uiState = awaitItem()
                        assertEquals(eventListSizeBefore + 1, uiState.userEvents.size)
                    }
                }
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}