package com.example.flashword.presentation.login

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.navigation.Destination
import com.example.flashword.presentation.navigation.navigateSingleTopTo
import com.example.flashword.presentation.registration.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen: Destination

fun NavHostController.navigateToLogin() {
    this.navigateSingleTopTo(LoginScreen)
}

fun NavGraphBuilder.loginDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    onSignUpClick: () -> Unit
) {
    composable<LoginScreen> {
        val viewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        LoginScreen(
            state = state,

            onSignUpClick = onSignUpClick
        )
    }


}