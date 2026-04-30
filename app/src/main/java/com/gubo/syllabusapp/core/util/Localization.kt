package com.gubo.syllabusapp.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gubo.syllabusapp.R
import java.time.DayOfWeek

@Composable
fun dayAsStr(dayOfWeek: DayOfWeek): String = when (dayOfWeek) {
    DayOfWeek.MONDAY -> stringResource(R.string.monday)
    DayOfWeek.TUESDAY -> stringResource(R.string.tuesday)
    DayOfWeek.WEDNESDAY -> stringResource(R.string.wednesday)
    DayOfWeek.THURSDAY -> stringResource(R.string.thursday)
    DayOfWeek.FRIDAY -> stringResource(R.string.friday)
    else -> ""
}