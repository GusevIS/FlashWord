package com.example.flashword.presentation.profile.settings

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreen: Destination

fun NavHostController.navigateToSettings() {
    this.navigateSingleTopTo(SettingsScreen)
}

fun NavGraphBuilder.settingsDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<SettingsScreen> {
//        val viewModel: SettingsViewModel = viewModel(factory = getViewModelFactory())

        SettingsScreen(
            navController = navController
        )
    }


}