package com.example.flashword.presentation.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    getViewModelFactory: () -> ViewModelProvider.Factory,
) {
    val viewModel: DashboardViewModel = viewModel(factory = getViewModelFactory())
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardScreenContent(
        uiState = state
    )

}

@Composable
fun DashboardScreenContent(
    modifier: Modifier = Modifier,
    uiState: DashboardUiState,
) {
    Text(
       text = uiState.userName
    )
}