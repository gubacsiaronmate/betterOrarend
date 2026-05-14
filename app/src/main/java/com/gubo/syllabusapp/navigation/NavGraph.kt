package com.gubo.syllabusapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gubo.syllabusapp.feature.schedule.ui.OrarendScreen
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun Navigaton(
    @ApplicationContext context: Context,
    content: @Composable () -> Unit,
) {
    val navController = NavHostController(context)

    NavHost(navController, startDestination = Route.Orarend) {
        composable<Route.Orarend> { OrarendScreen() }

        composable<Route.Settings> {  }
    }
}