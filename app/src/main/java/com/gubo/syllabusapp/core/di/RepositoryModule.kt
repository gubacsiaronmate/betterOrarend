package com.gubo.syllabusapp.core.di

import com.gubo.syllabusapp.feature.schedule.data.IcsParser
import com.gubo.syllabusapp.feature.schedule.data.ScheduleRepositoryImpl
import com.gubo.syllabusapp.feature.schedule.data.local.ClassSessionDao
import com.gubo.syllabusapp.feature.schedule.data.local.SemesterDao
import com.gubo.syllabusapp.feature.schedule.data.local.UserEventDao
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(
        semesterDao: SemesterDao,
        classSessionDao: ClassSessionDao,
        userEventDao: UserEventDao,
        icsParser: IcsParser
    ): ScheduleRepository = ScheduleRepositoryImpl(
        semesterDao = semesterDao,
        classSessionDao = classSessionDao,
        userEventDao = userEventDao,
        icsParser = icsParser
    )
}