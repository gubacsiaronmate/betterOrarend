package com.gubo.syllabusapp.core.di

import android.content.Context
import androidx.room.Room
import com.gubo.syllabusapp.core.database.SyllabusDatabase
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SyllabusDatabase =
        Room.databaseBuilder(
            context = context,
            klass = SyllabusDatabase::class.java,
            name = "syllabus.db"
        ).build()

    @Provides
    fun provideSemesterDao(db: SyllabusDatabase): SemesterDao = db.semesterDao()

    @Provides
    fun provideClassSessionDao(db: SyllabusDatabase): ClassSessionDao = db.classSessionDao()

    @Provides
    fun provideUserEntityDao(db: SyllabusDatabase): UserEventDao = db.userEventDao()
}