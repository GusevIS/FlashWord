package com.example.flashword.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.login.LoginViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    startDestination: String = NavigationItem.Login.route,
) {
    val getViewModelFactory: () -> ViewModelProvider.Factory = remember {
        { viewModelFactory }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination) {


        composable(NavigationItem.Login.route) {
            val viewModel: LoginViewModel = viewModel(factory = getViewModelFactory())
            val state by viewModel.state.collectAsStateWithLifecycle()

            LoginScreen(
                navController = navController,
                viewModel = viewModel
            ) {

            }
        }

        composable(NavigationItem.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                getViewModelFactory = getViewModelFactory
            )
        }

    }
}