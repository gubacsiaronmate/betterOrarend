package com.gubo.syllabusapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gubo.syllabusapp.core.ui.theme.AppTheme
import com.gubo.syllabusapp.feature.schedule.ui.OrarendScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) { paddingValues ->
                    OrarendScreen()
                    Box(Modifier.padding(paddingValues).size(0.dp))
                }
            }
        }
    }
}