package com.gubo.syllabusapp.feature.schedule.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassSessionDao {
    @Query("""
        SELECT * FROM class_sessions
        WHERE semesterId = :semesterId
        AND startTimeUtc >= :weekStartMillis
        AND startTimeUtc < :weekEndMillis
        ORDER BY startTimeUtc ASC
    """)
    fun getSessionForWeek(
        semesterId: Long,
        weekStartMillis: Long,
        weekEndMillis: Long
    ): Flow<List<ClassSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sessions: List<ClassSessionEntity>)

    @Query("DELETE FROM class_sessions WHERE semesterId = :semesterId")
    suspend fun deleteAllForSemester(semesterId: Long)
}