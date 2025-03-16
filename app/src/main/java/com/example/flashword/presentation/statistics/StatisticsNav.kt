package com.example.flashword.presentation.statistics

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
object StatisticsScreen: Destination

fun NavHostController.navigateToStatistics() {
    this.navigateSingleTopTo(StatisticsScreen)
}

fun NavGraphBuilder.statisticsDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<StatisticsScreen> {
      //  val viewModel: StatisticsViewModel = viewModel(factory = getViewModelFactory())

        StatisticsScreen(
            navController = navController,
        )
    }


}