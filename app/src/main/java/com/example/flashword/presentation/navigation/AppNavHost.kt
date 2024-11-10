package com.example.flashword.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.flashword.presentation.dashboard.dashboardDestination
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.login.loginDestination
import com.example.flashword.presentation.login.navigateToLogin
import com.example.flashword.presentation.registration.navigateToRegistration
import com.example.flashword.presentation.registration.registrationDestination

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory
) {
    val getViewModelFactory: () -> ViewModelProvider.Factory = remember {
        { viewModelFactory }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LoginScreen) {

        loginDestination(
            getViewModelFactory
        ) { navController.navigateToRegistration() }

        registrationDestination(
            getViewModelFactory
        ) { navController.navigateToLogin() }

        dashboardDestination(
            getViewModelFactory
        )

    }
}

