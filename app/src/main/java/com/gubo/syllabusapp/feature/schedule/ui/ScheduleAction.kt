package com.gubo.syllabusapp.feature.schedule.ui

import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent

sealed class ScheduleAction {
    object PreviousWeek : ScheduleAction()
    object NextWeek : ScheduleAction()
    data class AddUserEvent(val event: UserEvent) : ScheduleAction()
    object ResetWeekToToday : ScheduleAction()
}