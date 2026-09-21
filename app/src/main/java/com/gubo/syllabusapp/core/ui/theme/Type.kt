package com.gubo.syllabusapp.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.gubo.syllabusapp.R

val LoraFontFamily = FontFamily(
    Font(R.font.lora_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.lora_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.lora_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.lora_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.lora_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.lora_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.lora_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.lora_bold_italic, FontWeight.Bold, FontStyle.Italic),

)

private fun TextStyle.withAppFont() = copy(fontFamily = LoraFontFamily)

val AppTypography = Typography().run {
    Typography(
        displayLarge = displayLarge.withAppFont(),
        displayMedium = displayMedium.withAppFont(),
        displaySmall = displaySmall.withAppFont(),
        headlineLarge = headlineLarge.withAppFont(),
        headlineMedium = headlineMedium.withAppFont(),
        headlineSmall = headlineSmall.withAppFont(),
        titleLarge = titleLarge.withAppFont(),
        titleMedium = titleMedium.withAppFont(),
        titleSmall = titleSmall.withAppFont(),
        bodyLarge = bodyLarge.withAppFont(),
        bodyMedium = bodyMedium.withAppFont(),
        bodySmall = bodySmall.withAppFont(),
        labelLarge = labelLarge.withAppFont(),
        labelMedium = labelMedium.withAppFont(),
        labelSmall = labelSmall.withAppFont()
    )
}