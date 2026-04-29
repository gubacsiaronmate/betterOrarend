package com.gubo.syllabusapp.feature.schedule.ui

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import java.time.DayOfWeek
import java.time.LocalDate

data class ScheduleUiState(
    val sessions: Map<DayOfWeek, List<ClassSession>> = emptyMap(),
    val userEvents: Map<DayOfWeek, List<UserEvent>> = emptyMap(),
    val currentWeekStart: LocalDate = LocalDate.now().with(DayOfWeek.MONDAY),
    val activeSemester: Semester? = null,
    val isLoading: Boolean = false
)
