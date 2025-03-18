package com.example.flashword.presentation.addcard

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.flashword.FlashWordApp
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
data class AddCardScreen(
    val deckId: String,
    val deckTitle: String
): Destination

fun NavHostController.navigateToAddCard(deckId: String, deckTitle: String) {
    this.navigateSingleTopTo(AddCardScreen(deckId, deckTitle))
}

fun NavGraphBuilder.addCardDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<AddCardScreen> { backStackEntry ->
        val deckId = backStackEntry.toRoute<AddCardScreen>().deckId
        val deckTitle = backStackEntry.toRoute<AddCardScreen>().deckTitle
        val factory = (LocalContext.current.applicationContext as FlashWordApp).appComponent.addCardViewModelFactory()
        val viewModel: AddCardViewModel = viewModel(factory = factory.create(deckId, deckTitle))

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