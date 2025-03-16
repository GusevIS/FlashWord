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
import com.example.flashword.presentation.navigation.navigateToMainContent
import com.example.flashword.presentation.registration.navigateToRegistration
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen: Destination

fun NavHostController.navigateToLogin() {
    this.navigateSingleTopTo(LoginScreen)
}

fun NavGraphBuilder.loginDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<LoginScreen> {
        val viewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        LoginScreen(
            state = state,
            onUsernameChange = viewModel::updateEmail,
            onPasswordChange = viewModel::updatePassword,

            onSignInSuccessful = navController::navigateToMainContent,
            onSignInClick = viewModel::onSignInClick,
            onSignUpClick = navController::navigateToRegistration
        )
    }


}