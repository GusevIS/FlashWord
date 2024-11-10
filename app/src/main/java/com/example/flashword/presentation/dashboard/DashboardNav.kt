package com.example.flashword.presentation.dashboard

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import kotlinx.serialization.Serializable
import com.example.flashword.presentation.navigation.navigateSingleTopTo

@Serializable
object DashboardScreen: Destination

fun NavHostController.navigateToDashboard() {
    this.navigateSingleTopTo(DashboardScreen)
}

fun NavGraphBuilder.dashboardDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory
) {
    composable<DashboardScreen> {
        val viewModel: DashboardViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        DashboardScreen(
            state = state,
        )
    }


}