package com.gubo.syllabusapp.feature.schedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _weekStart = MutableStateFlow(
        LocalDate.now().with(DayOfWeek.MONDAY)
    )

    val uiState: StateFlow<ScheduleUiState> = combine(
        _weekStart.flatMapLatest { weekStart ->
            repository.getSessionsForWeek(weekStart)
        },
        _weekStart.flatMapLatest { weekStart ->
            repository.getUserEventsForWeek(weekStart)
        },
        _weekStart
    ) { sessions, events, weekStart ->
        ScheduleUiState(
            sessions = sessions.groupBy { it.startTime.dayOfWeek },
            userEvents = events.groupBy { it.startTime.dayOfWeek },
            currentWeekStart = weekStart,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScheduleUiState()
    )

    fun onAction(action: ScheduleAction) {
        when (action) {
            is ScheduleAction.PreviousWeek -> {
                _weekStart.value = _weekStart.value.minusWeeks(1)
            }

            is ScheduleAction.NextWeek -> {
                _weekStart.value = _weekStart.value.plusWeeks(1)
            }

            is ScheduleAction.AddUserEvent -> viewModelScope.launch {
                repository.addUserEvent(action.event)
            }
        }
    }
}