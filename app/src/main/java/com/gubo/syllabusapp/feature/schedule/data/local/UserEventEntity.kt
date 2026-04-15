package com.gubo.syllabusapp.feature.schedule.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_events",
    foreignKeys = [ForeignKey(
        entity = SemesterEntity::class,
        parentColumns = ["id"],
        childColumns = ["semesterId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("semesterId")]
)
data class UserEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val semesterId: Long,
    val title: String,
    val description: String?,
    val location: String?,
    val startTimeUtc: Long,
    val endTimeUtc: Long
)
