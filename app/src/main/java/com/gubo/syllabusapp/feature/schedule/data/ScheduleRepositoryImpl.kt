package com.gubo.syllabusapp.feature.schedule.data

import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterEntity
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.domain.model.Semester
import com.gubo.syllabusapp.feature.schedule.domain.model.UserEvent
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val semesterDao: SemesterDao,
    private val classSessionDao: ClassSessionDao,
    private val icsParser: IcsParser
) : ScheduleRepository {
    override fun getSessionsForWeek(weekStart: LocalDate): Flow<List<ClassSession>> {
        TODO()
    }

    override fun getUserEventsForWeek(weekStart: LocalDate): Flow<List<UserEvent>> {
        TODO()
    }

    override fun getAllSemesters(): Flow<List<Semester>> =
        semesterDao.getAllSemesters().map { list -> list.map { it.toDomain() } }

    override suspend fun importFromIcs(content: String, semesterName: String) {
        val semesterId = semesterDao.insert(
            SemesterEntity(
                name = semesterName,
                importedAt = System.currentTimeMillis(),
                isActive = false
            )
        )

        val sessions = icsParser.parse(content).mapIndexed { index, session ->
            session.toEntity(semesterId, "uid-$index")
        }

        classSessionDao.insertAll(sessions)
        semesterDao.switchActiveSemester(semesterId)
    }

    override suspend fun switchActiveSemester(id: Long) {
        semesterDao.switchActiveSemester(id)
    }

    override suspend fun deleteSemester(semester: Semester) {
        semesterDao.delete(semester.toEntity())
    }
}