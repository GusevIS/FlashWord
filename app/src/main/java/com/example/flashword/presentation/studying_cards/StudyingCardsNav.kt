package com.example.flashword.presentation.studying_cards

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
import com.example.flashword.domain.usecases.RecallQuality
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
data class StudyingCardsScreen(
    val deckId: String,
    val deckTitle: String
): Destination

fun NavHostController.navigateToStudyingCards(deckId: String, deckTitle: String) {
    this.navigateSingleTopTo(StudyingCardsScreen(deckId, deckTitle))
}

fun NavGraphBuilder.studyingCardsDestination(
    navController: NavHostController
) {
    composable<StudyingCardsScreen> { backStackEntry ->
        val deckId = backStackEntry.toRoute<StudyingCardsScreen>().deckId
        val deckTitle = backStackEntry.toRoute<StudyingCardsScreen>().deckTitle
        val factory = (LocalContext.current.applicationContext as FlashWordApp).appComponent.studyingCardsViewModelFactory()
        val viewModel: StudyingCardsViewModel = viewModel(factory = factory.create(deckId, deckTitle))

        val state by viewModel.state.collectAsStateWithLifecycle()

        StudyingCardsScreen(
            state = state,
            onPopBackClick = navController::popBackStack,
            onRecallClick = { recallQuality -> viewModel.processCardAnswer(recallQuality) },
            onShowAnswerClick = viewModel::rotate
        )
    }
}