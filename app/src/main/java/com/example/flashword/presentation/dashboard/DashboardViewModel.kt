package com.example.flashword.presentation.dashboard

import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val accountService: AccountService
): FlashAppViewModel() {
    private val _state = MutableStateFlow(DashboardUiState(accountService.currentUserEmail))
    val state = _state.asStateFlow()

    fun addDeck(title: String) {

    }

}