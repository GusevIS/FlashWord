package com.example.flashword.presentation.profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import kotlinx.serialization.Serializable

@Serializable
object ProfileScreen: Destination

fun NavHostController.navigateToProfile() {
    this.navigateSingleTopTo(ProfileScreen)
}

fun NavGraphBuilder.profileDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<ProfileScreen> {
        val viewModel: ProfileViewModel = viewModel(factory = getViewModelFactory())

        ProfileScreen(
            navController = navController,

            onLogoutClick = viewModel::signOut
        )
    }


}