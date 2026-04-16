package com.gubo.syllabusapp.feature.schedule.ui

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import java.time.DayOfWeek
import java.time.LocalDate

data class ScheduleUiState(
    val sessions: Map<DayOfWeek, ClassSession> = emptyMap(),
    val userEvents: Map<DayOfWeek, UserEvent> = emptyMap(),
    val currentWeekStart: LocalDate = LocalDate.now().with(DayOfWeek.MONDAY),
    val activeSemester: Semester? = null,
    val isLoading: Boolean = false
)
