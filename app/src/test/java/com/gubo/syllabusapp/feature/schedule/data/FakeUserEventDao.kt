package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.data.local.UserEventDao
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeUserEventDao : UserEventDao {

    private val events = MutableStateFlow<List<UserEventEntity>>(emptyList())
    private var nextId: Long = 1

    override fun getUserEventsForWeek(
        semesterId: Long,
        weekStartMillis: Long,
        weekEndMillis: Long
    ): Flow<List<UserEventEntity>> =
        events.map { list ->
            list.filter {
                it.semesterId == semesterId &&
                it.startTimeUtc >= weekStartMillis &&
                it.endTimeUtc < weekEndMillis
            }
        }

    override suspend fun insert(event: UserEventEntity): Long {
        val id = nextId++
        events.value += event.copy(id = id)
        return id
    }

    override suspend fun update(event: UserEventEntity) {
        events.value = events.value.map {
            if (it.id == event.id) event
            else it
        }
    }

    override suspend fun delete(event: UserEventEntity) {
        events.value = events.value.filter { it.id != event.id }
    }
}