package com.example.flashword.presentation.dashboard

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flashword.presentation.addcard.navigateToAddCard
import com.example.flashword.presentation.navigation.Destination
import kotlinx.serialization.Serializable
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import com.example.flashword.presentation.studying_cards.navigateToStudyingCards

@Serializable
object DashboardScreen: Destination

fun NavHostController.navigateToDashboard() {
    this.navigateSingleTopTo(DashboardScreen)
}

fun NavGraphBuilder.dashboardDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<DashboardScreen> {
        val viewModel: DashboardViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        val backStackEntry = navController.currentBackStackEntryAsState()

        DashboardScreen(
            state = state,
            onSync = viewModel::synchronize,
            onAddCardClick = navController::navigateToAddCard,
            onDeckClick = navController::navigateToStudyingCards,
            onReviewAllClick = {},
            onAddDeckClick = viewModel::addDeck,
        )
    }
}