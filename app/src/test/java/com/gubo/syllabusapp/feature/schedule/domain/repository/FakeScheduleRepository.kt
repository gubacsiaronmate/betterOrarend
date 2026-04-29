package com.gubo.syllabusapp.feature.schedule.domain.repository

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

class FakeScheduleRepository : ScheduleRepository {
    private val semesters = MutableStateFlow<List<Semester>>(emptyList())
    private val sessions = MutableStateFlow<List<ClassSession>>(emptyList())
    private val userEvents = MutableStateFlow<List<UserEvent>>(emptyList())

    override fun getSessionsForWeek(weekStart: LocalDate): Flow<List<ClassSession>> = sessions

    override fun getUserEventsForWeek(weekStart: LocalDate): Flow<List<UserEvent>> = userEvents

    override fun getAllSemesters(): Flow<List<Semester>> = semesters

    override suspend fun importFromIcs(content: String, semesterName: String) {
        val semester = Semester(1, semesterName, 0, true)
        semesters.value += semester
    }

    override suspend fun addUserEvent(event: UserEvent) {
        userEvents.value += event
    }

    override suspend fun switchActiveSemester(id: Long) {}

    override suspend fun deleteSemester(semester: Semester) {
        semesters.value = semesters.value.filter { it != semester }
    }
}