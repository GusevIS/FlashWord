package com.example.flashword.presentation.appstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    viewModel: AuthViewModel
): AppState {

    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()

    return remember(user, navController) {
        AppState(
            navController,
            user
        )
    }
}

@Stable
data class AppState(
    val navHostController: NavHostController,
    val user: UserModelUI? = UserModelUI()
)