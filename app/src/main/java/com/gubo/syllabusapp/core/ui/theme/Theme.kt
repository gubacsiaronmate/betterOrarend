package com.gubo.syllabusapp.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.gubo.syllabusapp.core.ui.theme.settings.DarkModePreference
import com.gubo.syllabusapp.core.ui.theme.settings.ThemeSettings
import com.materialkolor.ktx.isLight
import com.materialkolor.rememberDynamicColorScheme

const val DefaultSeedColor = 0xFF63568F

@Composable
fun AppTheme(
    settings: ThemeSettings,
    content: @Composable () -> Unit
) {
    val colorScheme = if (settings.dynamicColor) {
        val context = LocalContext.current
        if (settings.darkModePreference.isDarkTheme())
            dynamicDarkColorScheme(context)
        else dynamicLightColorScheme(context)
    } else rememberDynamicColorScheme(
        seedColor = settings.seedColor ?: Color(DefaultSeedColor),
        isDark = settings.darkModePreference.isDarkTheme(),
        contrastLevel = settings.contrast.value,
        isAmoled = settings.isAmoled
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
@ReadOnlyComposable
private fun DarkModePreference.isDarkTheme(): Boolean = when (this) {
    DarkModePreference.FollowSystem -> isSystemInDarkTheme()
    DarkModePreference.Light -> false
    DarkModePreference.Dark -> true
}

val ColorScheme.classStripe: Color
    @Composable
    @ReadOnlyComposable
    get() = if (surface.isLight())
        classStripeLight else classStripeDark

val ColorScheme.eventStripe: Color
    @Composable
    @ReadOnlyComposable
    get() = if (surface.isLight())
        eventStripeLight else eventStripeDark