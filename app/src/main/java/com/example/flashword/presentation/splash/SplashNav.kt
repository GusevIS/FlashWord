package com.example.flashword.presentation.splash

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateAndPopUp
import kotlinx.serialization.Serializable

@Serializable
object SplashScreen: Destination

fun NavGraphBuilder.splashDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<SplashScreen> {
        val viewModel: SplashViewModel = viewModel(factory = getViewModelFactory())

        SplashScreen(
            //openAndPopUp = { route, popUp -> navController.navigateAndPopUp(route, popUp)},
            viewModel = viewModel
        )

    }

}