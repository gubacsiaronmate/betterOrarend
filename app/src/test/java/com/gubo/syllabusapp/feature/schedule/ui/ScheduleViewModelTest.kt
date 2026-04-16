package com.gubo.syllabusapp.feature.schedule.ui

import com.gubo.syllabusapp.feature.schedule.domain.repository.FakeScheduleRepository
import com.gubo.syllabusapp.feature.schedule.domain.repository.ScheduleRepository
import org.junit.Before
import org.junit.Test

class ScheduleViewModelTest {
    private lateinit var repository: ScheduleRepository
    private lateinit var viewModel: ScheduleViewModel

    @Before
    fun setup() {
        repository = FakeScheduleRepository()
        viewModel = ScheduleViewModel(repository)
    }

    @Test
    fun `viewmodel inital state`() {

    }
}