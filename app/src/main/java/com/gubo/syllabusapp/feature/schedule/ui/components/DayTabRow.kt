package com.gubo.syllabusapp.feature.schedule.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gubo.syllabusapp.feature.schedule.ui.days

@Composable
fun DayTabRow(
    pagerState: PagerState,
    onTabClick: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        days.forEachIndexed { idx, day ->
            val selected = pagerState.currentPage == idx

            Text(
                text = day,
                style = MaterialTheme.typography.labelLarge,
                color =
                    if (selected)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .background(
                        color =
                            if (selected)
                                MaterialTheme.colorScheme.primary
                            else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onTabClick(idx) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}