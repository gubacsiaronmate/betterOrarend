package com.gubo.syllabusapp.feature.schedule.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayPage(
    sessions: List<ClassSession>,
    onSessionClick: (ClassSession) -> Unit
) {
    if (sessions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nics ora.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        return
    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(sessions) { idx, session ->
            SessionCard(idx + 1, session) {
                onSessionClick(session)
            }
        }
    }
}