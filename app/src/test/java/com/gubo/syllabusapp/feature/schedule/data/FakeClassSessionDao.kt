package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeClassSessionDao : ClassSessionDao {

    private val sessions = MutableStateFlow<List<ClassSessionEntity>>(emptyList())

    override fun getSessionForWeek(
        semesterId: Long,
        weekStartMillis: Long,
        weekEndMillis: Long
    ): Flow<List<ClassSessionEntity>> =
        sessions.map { list ->
            list.filter {
                it.semesterId == semesterId &&
                it.startTimeUtc >= weekStartMillis &&
                it.startTimeUtc < weekEndMillis
            }
        }

    override suspend fun insertAll(sessions: List<ClassSessionEntity>) {
        this.sessions.value += sessions
    }

    override suspend fun deleteAllForSemester(semesterId: Long) {
        sessions.value = sessions.value.filter { it.semesterId != semesterId }
    }
}