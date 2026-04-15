package com.gubo.syllabusapp.feature.schedule.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "class_sessions",
    foreignKeys = [ForeignKey(
        entity = SemesterEntity::class,
        parentColumns = ["id"],
        childColumns = ["semesterId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("semesterId")]
)
data class ClassSessionEntity(
    @PrimaryKey val uid: String,
    val semesterId: Long,
    val subject: String,
    val instructor: String,
    val location: String,
    val startTimeUtc: Long,
    val endTimeUtc: Long
)
