package com.gubo.syllabusapp.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gubo.syllabusapp.core.settings.domain.DarkModePreference
import com.gubo.syllabusapp.core.settings.domain.ThemeSettings
import com.gubo.syllabusapp.core.settings.domain.ThemeSettingsRepository
import com.materialkolor.Contrast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemeSettingsRepository
) : ViewModel() {
    val settings: StateFlow<ThemeSettings?> = repository.settings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun setDarkMode(value: DarkModePreference) {
        viewModelScope.launch { repository.setDarkMode(value) }
    }

    fun setDynamicColor(enabled: Boolean) {
        viewModelScope.launch { repository.setDynamicColor(enabled) }
    }

    fun setContrast(value: Contrast) {
        viewModelScope.launch { repository.setContrast(value) }
    }

    fun setSeedColor(value: Color?) {
        viewModelScope.launch { repository.setSeedColor(value) }
    }

    fun setAmoled(enabled: Boolean) {
        viewModelScope.launch { repository.setAmoled(enabled) }
    }
}