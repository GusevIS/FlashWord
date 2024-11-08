package com.example.flashword.presentation

enum class Screen {
    DASHBOARD,
    SETTINGS,
    STATISTICS,

    LOGIN,
    REGISTRATION,
    FORGOT_PASSWORD

}

sealed class NavigationItem(val route: String) {
    object Dashboard: NavigationItem(Screen.DASHBOARD.name)
    object Settings: NavigationItem(Screen.SETTINGS.name)
    object Statistics: NavigationItem(Screen.STATISTICS.name)

    object Login: NavigationItem(Screen.LOGIN.name)
    object Registration: NavigationItem(Screen.REGISTRATION.name)
    object ForgotPassword: NavigationItem(Screen.FORGOT_PASSWORD.name)
}