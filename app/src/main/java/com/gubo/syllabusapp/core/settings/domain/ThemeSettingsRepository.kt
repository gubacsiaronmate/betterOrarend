package com.gubo.syllabusapp.core.settings.domain

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import kotlinx.coroutines.flow.Flow

interface ThemeSettingsRepository {
    val settings: Flow<ThemeSettings>
    suspend fun setDarkMode(value: DarkModePreference)
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setContrast(value: Contrast)
    suspend fun setSeedColor(value: Color?)
    suspend fun setAmoled(enabled: Boolean)
}