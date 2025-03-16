package com.example.flashword.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainContentBottomBar(
    navController: NavHostController,
    destinations: List<TopLevelDestination> = listOf(),
) {
    NavigationBar(
        modifier = Modifier
            .shadow(
            elevation = 16.dp,
            shape = RectangleShape
        ),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        destinations.forEachIndexed { index, item ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.destination::class) } == true
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) },
                selected = selected,
//                selected = selectedItem == index,
                onClick = {
                    navController.navigate(item.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    selectedIndicatorColor = MaterialTheme.colorScheme.background,
                    selectedIconColor = MaterialTheme.colorScheme.error,
                    selectedTextColor = MaterialTheme.colorScheme.error)
            )
        }
    }
}