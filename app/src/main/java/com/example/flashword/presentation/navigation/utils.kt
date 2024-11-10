package com.example.flashword.presentation.navigation

import androidx.navigation.NavHostController

interface Destination

fun NavHostController.navigateSingleTopTo(route: Destination) =
    this.navigate(route) { launchSingleTop = true }