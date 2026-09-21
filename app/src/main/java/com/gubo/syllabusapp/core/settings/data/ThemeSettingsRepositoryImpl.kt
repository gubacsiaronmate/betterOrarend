package com.gubo.syllabusapp.core.settings.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.gubo.syllabusapp.core.settings.domain.DarkModePreference
import com.gubo.syllabusapp.core.settings.domain.ThemeSettings
import com.gubo.syllabusapp.core.settings.domain.ThemeSettingsRepository
import com.materialkolor.Contrast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ThemeSettingsRepositoryImpl(
    val dataStore: DataStore<Preferences>
) : ThemeSettingsRepository {
    private object Keys {
        val DarkMode = stringPreferencesKey("dark_mode")
        val DynamicColor = booleanPreferencesKey("dynamic_color")
        val Contrast = stringPreferencesKey("contrast")
        val SeedColor = intPreferencesKey("seed_color")
        val Amoled = booleanPreferencesKey("amoled")
    }

    override val settings: Flow<ThemeSettings> = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { preferences ->
            ThemeSettings(
                darkModePreference = preferences[Keys.DarkMode]
                    .toEnum(DarkModePreference.FollowSystem),
                dynamicColor = preferences[Keys.DynamicColor] ?: false,
                contrast = preferences[Keys.Contrast].toEnum(Contrast.Default),
                seedColor = preferences[Keys.SeedColor]?.let { Color(it) },
                isAmoled = preferences[Keys.Amoled] ?: false
            )
        }

    override suspend fun setDarkMode(value: DarkModePreference) {
        dataStore.edit { it[Keys.DarkMode] = value.name }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.edit { it[Keys.DynamicColor] = enabled }
    }

    override suspend fun setContrast(value: Contrast) {
        dataStore.edit { it[Keys.Contrast] = value.name }
    }

    override suspend fun setSeedColor(value: Color?) {
        if (value == null) dataStore.edit { it.remove(Keys.SeedColor) }
        else dataStore.edit { it[Keys.SeedColor] = value.toArgb() }
    }

    override suspend fun setAmoled(enabled: Boolean) {
        dataStore.edit { it[Keys.Amoled] = enabled }
    }

    private inline fun <reified T : Enum<T>> String?.toEnum(default: T): T =
        this?.let { runCatching { enumValueOf<T>(it) }.getOrNull() } ?: default
}