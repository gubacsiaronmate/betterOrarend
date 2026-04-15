package com.gubo.syllabusapp.feature.schedule.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserEventDao {
    @Query("""
        SELECT * FROM user_events
        WHERE semesterId = :semesterId
        AND startTimeUtc >= :weekStartMillis
        AND endTimeUtc < :weekEndMillis
        ORDER BY startTimeUtc ASC
    """)
    fun getUserEventsForWeek(
        semesterId: Long,
        weekStartMillis: Long,
        weekEndMillis: Long
    ): Flow<List<UserEventEntity>>

    @Insert
    suspend fun insert(event: UserEventEntity): Long

    @Update
    suspend fun update(event: UserEventEntity)

    @Delete
    suspend fun delete(event: UserEventEntity)
}