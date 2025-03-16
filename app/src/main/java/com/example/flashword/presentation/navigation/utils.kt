package com.example.flashword.presentation.navigation

import androidx.navigation.NavHostController

interface Destination

fun NavHostController.navigateSingleTopTo(route: Destination) =
    this.navigate(route) { launchSingleTop = true }

fun NavHostController.clearAndNavigate(route: Destination) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(0) { inclusive = true }
    }
}

fun NavHostController.navigateAndPopUp(route: Destination, popUp: Destination) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(popUp) { inclusive = true }
    }
}