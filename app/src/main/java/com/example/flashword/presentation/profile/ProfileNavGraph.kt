package com.example.flashword.presentation.profile

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.profile.settings.settingsDestination
import kotlinx.serialization.Serializable

@Serializable
object ProfileGraphDestination: Destination

fun NavGraphBuilder.profileNavGraph(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {

    navigation<ProfileGraphDestination>(startDestination = ProfileScreen) {

        profileDestination(
            getViewModelFactory,
            navController
        )

        settingsDestination(
            getViewModelFactory,
            navController
        )

    }

}