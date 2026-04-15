package com.gubo.syllabusapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionEntity
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterEntity
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventDao
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventEntity

@Database(
    entities = [
        SemesterEntity::class,
        ClassSessionEntity::class,
        UserEventEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class SyllabusDatabase : RoomDatabase() {
    abstract fun semesterDao(): SemesterDao
    abstract fun classSessionDao(): ClassSessionDao
    abstract fun userEventDao(): UserEventDao
}