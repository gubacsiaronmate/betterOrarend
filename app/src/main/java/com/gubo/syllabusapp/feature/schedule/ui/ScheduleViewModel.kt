package com.gubo.syllabusapp.feature.schedule.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val repository: ScheduleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    fun onAction(action: ScheduleAction) {
        when (action) {
            is ScheduleAction.PreviousWeek -> _uiState.update { state ->
                state.copy(currentWeekStart = state.currentWeekStart.minusWeeks(1))
            }
            is ScheduleAction.NextWeek -> _uiState.update { state ->
                state.copy(currentWeekStart = state.currentWeekStart.plusWeeks(1))
            }
            is ScheduleAction.AddUserEvent -> viewModelScope.launch {
                repository.addUserEvent(action.event)
                _uiState.update { state ->
                    val key = action.event.startTime.dayOfWeek
                    val userEvents = state.userEvents.toMutableMap()
                    val events = userEvents[key] ?: emptyList()
                    userEvents[key] = events + action.event
                    state.copy(userEvents = userEvents)
                }
            }
        }
    }
}