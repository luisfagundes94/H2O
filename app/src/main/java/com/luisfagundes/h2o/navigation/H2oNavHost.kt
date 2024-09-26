package com.luisfagundes.h2o.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luisfagundes.h2o.features.history.HistoryRoute
import com.luisfagundes.h2o.features.settings.SettingsRoute
import com.luisfagundes.h2o.features.water.WaterRoute

@Composable
fun H2oNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Water,
        modifier = modifier,
    ) {
        composable<Water> {
            WaterRoute(
                onNavigateToHistory = { navController.navigate(History) },
                onNavigateToSettings = { navController.navigate(Settings) },
            )
        }
        composable<Settings> {
            SettingsRoute(
                onBackPressed = navController::popBackStack,
            )
        }
        composable<History> {
            HistoryRoute(
                onBackPressed = navController::popBackStack,
            )
        }
    }
}
