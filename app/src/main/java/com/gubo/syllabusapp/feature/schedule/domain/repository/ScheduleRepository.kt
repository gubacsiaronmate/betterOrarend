package com.gubo.syllabusapp.feature.schedule.domain.repository

import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ScheduleRepository {
    fun getSessionsForWeek(weekStart: LocalDate): Flow<List<ClassSession>>
    fun getUserEventsForWeek(weekStart: LocalDate): Flow<List<UserEvent>>
    fun getAllSemesters(): Flow<List<Semester>>
    suspend fun importFromIcs(content: String, semesterName: String)
    suspend fun addUserEvent(event: UserEvent)
    suspend fun switchActiveSemester(id: Long)
    suspend fun deleteSemester(semester: Semester)
}