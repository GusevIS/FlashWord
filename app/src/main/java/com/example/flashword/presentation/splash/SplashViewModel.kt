package com.example.flashword.presentation.splash

import com.example.flashword.FlashAppViewModel
import com.example.flashword.domain.repos.AccountService
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.login.LoginScreen
import com.example.flashword.presentation.navigation.Destination
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val accountService: AccountService
): FlashAppViewModel() {

    fun onAppStart(openAndPopUp: (Destination, Destination) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(DashboardScreen, SplashScreen)
        else openAndPopUp(LoginScreen, SplashScreen)

    }

}