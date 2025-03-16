package com.example.flashword.presentation.addcard

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.dashboard.DashboardViewModel
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
object AddCardScreen: Destination

fun NavHostController.navigateToAddCard() {
    this.navigateSingleTopTo(AddCardScreen)
}

fun NavGraphBuilder.addCardDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<AddCardScreen> {
        val viewModel: AddCardViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        AddCardScreen(
            state = state,
            onPopBackClick = navController::popBackStack,

            onTurnOverCardClick = viewModel::rotate,
            onAddClick = viewModel::addCard,
            updateFrontText = viewModel::updateFrontText,
            updateBackText = viewModel::updateBackText,

        )
    }
}