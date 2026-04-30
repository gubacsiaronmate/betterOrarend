package com.gubo.syllabusapp.feature.schedule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gubo.syllabusapp.core.util.dayAsStr
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DayTabRow(
    date: LocalDate,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy. MM. dd.") }
    val formattedDate = date.format(formatter)

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
            .padding(top = 4.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        IconButton(onPreviousClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBackIos, null)
        }

        Text("$formattedDate ${dayAsStr(date.dayOfWeek)}")

        IconButton(onNextClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, null)
        }
    }
}