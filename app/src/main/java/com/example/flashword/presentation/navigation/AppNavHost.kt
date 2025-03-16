package com.example.flashword.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.flashword.presentation.appstate.AppState
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.login.loginDestination
import com.example.flashword.presentation.registration.registrationDestination
import com.example.flashword.presentation.splash.SplashScreen
import com.example.flashword.presentation.splash.splashDestination
import kotlinx.coroutines.delay

const val SPLASH_TIMEOUT = 2000L

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: AppState = AppState(rememberNavController()),
    navController: NavHostController,
    getViewModelFactory: () -> ViewModelProvider.Factory,
) {
    val user = appState.user
    var isAppInitializing by remember { mutableStateOf(true) }

    LaunchedEffect(user) {
        if (isAppInitializing ) {
            delay(SPLASH_TIMEOUT)
            isAppInitializing  = false
        }
    }

    val startDestination = when {
        isAppInitializing -> SplashScreen
        user == null -> LoginScreen
        else -> MainContentDestination
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        splashDestination(
            getViewModelFactory,
            navController
        )

        loginDestination(
            getViewModelFactory,
            navController
        )

        registrationDestination(
            getViewModelFactory,
            navController
        )

        mainContentDestination(
            getViewModelFactory,
            navController
        )

    }

    LaunchedEffect(user, isAppInitializing) {
        if (!isAppInitializing) {
            if (user == null) {
                appState.navHostController.clearAndNavigate(LoginScreen)
            } else {
                navController.clearAndNavigate(MainContentDestination)
            }

        }
    }

}
