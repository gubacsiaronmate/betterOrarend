package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeSemesterDao : SemesterDao {

    private val semesters = MutableStateFlow<List<SemesterEntity>>(emptyList())
    private var nextId: Long = 1

    override fun getAllSemesters(): Flow<List<SemesterEntity>> = semesters

    override fun getActiveSemester(): Flow<SemesterEntity?> =
        semesters.map { list -> list.firstOrNull { it.isActive } }

    override suspend fun insert(semester: SemesterEntity): Long {
        val id = nextId++
        semesters.value += semester.copy(id = id)
        return id
    }

    override suspend fun deactivateAll() {
        semesters.value = semesters.value.map { it.copy(isActive = false) }
    }

    override suspend fun setActive(id: Long) {
        semesters.value = semesters.value.map {
            if (it.id != id) it
            else it.copy(isActive = true)
        }
    }

    override suspend fun delete(semester: SemesterEntity) {
        semesters.value = semesters.value.filter { it.id != semester.id }
    }
}