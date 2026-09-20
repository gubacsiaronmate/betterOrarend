package com.gubo.syllabusapp.core.settings.domain


import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast

data class ThemeSettings(
    val darkModePreference: DarkModePreference = DarkModePreference.FollowSystem,
    val dynamicColor: Boolean = false,
    val contrast: Contrast = Contrast.Default,
    val seedColor: Color? = null,
    val isAmoled: Boolean = false
)
