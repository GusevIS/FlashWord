package com.example.flashword.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.flashword.presentation.login.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DashboardViewModel @Inject constructor(


): ViewModel() {
    private val _state = MutableStateFlow(DashboardUiState())
    val state = _state.asStateFlow()
}