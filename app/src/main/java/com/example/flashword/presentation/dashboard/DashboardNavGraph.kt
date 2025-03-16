package com.example.flashword.presentation.dashboard

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.flashword.presentation.addcard.addCardDestination
import com.example.flashword.presentation.navigation.Destination
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


    }
}