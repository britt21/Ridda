package com.mobile.ridda.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.ridda.ui.RideScreen
import com.mobile.ridda.ui.RideHistoryScreen

object Destinations {
    const val RIDE = "ride"
    const val HISTORY = "history"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Destinations.RIDE) {
        composable(Destinations.RIDE) {
            RideScreen(navController = navController)
        }
        composable(Destinations.HISTORY) {
            RideHistoryScreen(navController = navController)
        }
    }
}