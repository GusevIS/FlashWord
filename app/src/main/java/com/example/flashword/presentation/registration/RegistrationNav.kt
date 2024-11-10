package com.example.flashword.presentation.registration

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import kotlinx.serialization.Serializable
import com.example.flashword.presentation.navigation.navigateSingleTopTo

@Serializable
object RegistrationScreen: Destination

fun NavHostController.navigateToRegistration() {
    this.navigateSingleTopTo(RegistrationScreen)
}

fun NavGraphBuilder.registrationDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onSignInClick: () -> Unit
) {
    composable<RegistrationScreen> {
        val viewModel: RegistrationViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        RegistrationScreen(
            state = state,

            onSignInClick = onSignInClick
        )
    }
}