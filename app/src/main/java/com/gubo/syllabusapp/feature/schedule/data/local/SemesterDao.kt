package com.gubo.syllabusapp.feature.schedule.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface SemesterDao {
    @Query("SELECT * FROM semesters ORDER BY importedAt DESC")
    fun getAllSemesters(): Flow<List<SemesterEntity>>

    @Query("SELECT * FROM semesters WHERE isActive = 1 LIMIT 1")
    fun getActiveSemester(): Flow<SemesterEntity?>

    @Insert
    suspend fun insert(semester: SemesterEntity): Long

    @Query("UPDATE semesters SET isActive = 0")
    suspend fun deactivateAll()

    @Query("UPDATE semesters SET isActive = 1 WHERE id = :id")
    suspend fun setActive(id: Long)

    @Transaction
    suspend fun switchActiveSemester(id: Long) {
        deactivateAll()
        setActive(id)
    }

    @Delete
    suspend fun delete(semester: SemesterEntity)
}