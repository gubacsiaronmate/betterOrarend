package com.gubo.syllabusapp.feature.schedule.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gubo.syllabusapp.core.util.asFormattedStr
import com.gubo.syllabusapp.feature.schedule.domain.model.ClassSession
import com.gubo.syllabusapp.feature.schedule.ui.components.DayPage
import com.gubo.syllabusapp.feature.schedule.ui.components.DayTabRow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrarendScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val todayIndex = (LocalDate.now().dayOfWeek.value - 1)
    val pagerState = rememberPagerState(
        initialPage = todayIndex,
        pageCount = { 7 }
    )
    val scope = rememberCoroutineScope()
    var selectedSession by remember { mutableStateOf<ClassSession?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Box(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                DayPage(
                    sessions = uiState.sessions[DayOfWeek.of(page + 1)] ?: emptyList()
                ) { selectedSession = it }
            }

            DayTabRow(
                date = uiState
                    .currentWeekStart
                    .plusDays(pagerState.currentPage.toLong()),
                modifier = Modifier.align(Alignment.BottomCenter),
                onPreviousClick = {
                    scope.launch {
                        viewModel.onAction(ScheduleAction.PreviousWeek)
                    }
                },
                onNextClick = {
                    scope.launch {
                        viewModel.onAction(ScheduleAction.NextWeek)
                    }
                }
            )
        }

        selectedSession?.let { session ->
            ModalBottomSheet(
                onDismissRequest = { selectedSession = null },
                sheetState = sheetState
            ) {
                ModalBottomSheetContent(session)
            }
        }
    }
}

@Composable
fun ModalBottomSheetContent(
    session: ClassSession
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = session.subject,
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider()
        Text(
            text = "Oktató: ${session.instructor}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Terem: ${session.location}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${session.startTime.asFormattedStr()} – ${session.endTime.asFormattedStr()}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}