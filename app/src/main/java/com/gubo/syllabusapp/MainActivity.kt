package com.gubo.syllabusapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gubo.syllabusapp.core.ui.theme.AppTheme
import com.gubo.syllabusapp.core.ui.theme.ThemeViewModel
import com.gubo.syllabusapp.feature.schedule.ui.OrarendScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { themeViewModel.settings.value == null }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settings by themeViewModel.settings.collectAsStateWithLifecycle()
            settings?.let {
                AppTheme(it) {
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
}