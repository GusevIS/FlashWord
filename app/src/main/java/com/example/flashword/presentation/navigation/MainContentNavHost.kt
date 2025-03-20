package com.example.flashword.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flashword.R
import com.example.flashword.presentation.addcard.AddCardScreen
import com.example.flashword.presentation.addcard.addCardDestination
import com.example.flashword.presentation.dashboard.DashboardGraphDestination
import com.example.flashword.presentation.dashboard.DashboardScreen
import com.example.flashword.presentation.dashboard.dashboardDestination
import com.example.flashword.presentation.dashboard.dashboardNavGraph
import com.example.flashword.presentation.profile.ProfileScreen
import com.example.flashword.presentation.profile.profileDestination
import com.example.flashword.presentation.profile.profileNavGraph
import com.example.flashword.presentation.statistics.StatisticsScreen
import com.example.flashword.presentation.statistics.statisticsDestination
import com.example.flashword.presentation.studying_cards.StudyingCardsScreen
import kotlinx.serialization.Serializable

@Serializable
object MainContentDestination: Destination

fun NavHostController.navigateToMainContent() {
    this.navigateSingleTopTo(MainContentDestination)
}

fun NavGraphBuilder.mainContentDestination(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    composable<MainContentDestination> {
        MainContentNavHost(
            getViewModelFactory,
            navController
        )
    }
}

data class TopLevelDestination(
    val name: String = "",
    val destination: Destination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun MainContentNavHost(
    getViewModelFactory: () -> ViewModelProvider.Factory,
    navController: NavHostController
) {
    val topLevelDestinations = listOf(
        TopLevelDestination("Statistics", StatisticsScreen, ImageVector.vectorResource(R.drawable.stats_24),
            ImageVector.vectorResource(R.drawable.stats_24)),
        TopLevelDestination("Dashboard", DashboardGraphDestination, ImageVector.vectorResource(R.drawable.cards_blank_solid_24),
            ImageVector.vectorResource(R.drawable.cards_blank_outlined_24)),
        TopLevelDestination("Profile", ProfileScreen, ImageVector.vectorResource(R.drawable.user_solid_24),
            ImageVector.vectorResource(R.drawable.user_outlined_24)),

        )

    val mainContentNavController = rememberNavController()

    var showBottomBar by rememberSaveable { (mutableStateOf(true)) }
    var showTopBar by rememberSaveable { (mutableStateOf(false)) }

    val navBackStackEntry by mainContentNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination != null) {
        when {
            currentDestination.hasRoute(DashboardScreen::class)
                 or currentDestination.hasRoute(StatisticsScreen::class) or
                    currentDestination.hasRoute(ProfileScreen::class) -> {
                showBottomBar = true
                showTopBar = false
            }
            currentDestination.hasRoute(AddCardScreen::class) or
                    currentDestination.hasRoute(StudyingCardsScreen::class)-> {
                showBottomBar = false
                showTopBar = true
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {

            if (showBottomBar) {
                MainContentBottomBar(
                    mainContentNavController,
                    topLevelDestinations
                )
            }

        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = mainContentNavController,
                startDestination = DashboardGraphDestination,
                modifier = Modifier.fillMaxSize()
            ) {
                dashboardNavGraph(getViewModelFactory, mainContentNavController)
                statisticsDestination(getViewModelFactory, mainContentNavController)
                profileNavGraph(getViewModelFactory, mainContentNavController)
            }
        }

    }

}