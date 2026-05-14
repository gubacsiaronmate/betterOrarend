package com.gubo.syllabusapp.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable data object Orarend : Route()
    @Serializable data object Settings : Route()
}