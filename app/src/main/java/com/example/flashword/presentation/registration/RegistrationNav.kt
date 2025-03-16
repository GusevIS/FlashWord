package com.example.flashword.presentation.registration

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.flashword.presentation.dashboard.navigateToDashboard
import com.example.flashword.presentation.login.navigateToLogin
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
    navController: NavHostController,
) {
    composable<RegistrationScreen> {
        val viewModel: RegistrationViewModel = viewModel(factory = getViewModelFactory())
        val state by viewModel.state.collectAsStateWithLifecycle()

        RegistrationScreen(
            state = state,
            onUsernameChange = viewModel::updateUsername,
            onEmailChange = viewModel::updateEmail,
            onPasswordChange = viewModel::updatePassword,
            onConfirmedPasswordChange = viewModel::updateConfirmedPassword,

            onSignUpClick = viewModel::onSignUpClick,

            onSignInClick = navController::navigateToLogin,
            onSignUpSuccessful = navController::navigateToLogin
        )
    }
}