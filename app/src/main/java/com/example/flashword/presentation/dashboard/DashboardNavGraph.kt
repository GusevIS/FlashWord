package com.example.flashword.presentation.dashboard

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.flashword.presentation.addcard.addCardDestination
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.studying_cards.studyingCardsDestination
import kotlinx.serialization.Serializable

@Serializable
object DashboardGraphDestination: Destination

fun NavGraphBuilder.dashboardNavGraph(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    navigation<DashboardGraphDestination>(startDestination = DashboardScreen) {
        dashboardDestination(
            getViewModelFactory,
            navController
        )

        addCardDestination(
            getViewModelFactory,
            navController
        )

        studyingCardsDestination(
            navController
        )


    }
}