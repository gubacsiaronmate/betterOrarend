package com.gubo.syllabusapp.core.ui.theme.settings


import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast

data class ThemeSettings(
    val darkModePreference: DarkModePreference = DarkModePreference.FollowSystem,
    val dynamicColor: Boolean = false,
    val contrast: Contrast = Contrast.Default,
    val seedColor: Color? = null,
    val isAmoled: Boolean = false
)
