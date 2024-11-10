package com.example.flashword.presentation.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: DashboardUiState = DashboardUiState()
) {
//    val viewModel: DashboardViewModel = viewModel(factory = getViewModelFactory())
//    val state by viewModel.state.collectAsStateWithLifecycle()

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