package com.gubo.syllabusapp.feature.schedule.domain.model

data class Semester(
    val id: Long,
    val name: String,
    val importedAt: Long,
    val isActive: Boolean
)
