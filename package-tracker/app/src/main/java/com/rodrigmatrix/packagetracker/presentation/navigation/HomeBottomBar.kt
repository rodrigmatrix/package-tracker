package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun HomeBottomBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route?.contains("package")?.not() == true) {
        NavigationBar {
            HomeRoutes.values().forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.image, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    onClick = {
                        navController.navigate(screen.route) {
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    selected = currentDestination.hierarchy.any {
                        it.route == screen.route
                    }
                )
            }
        }
    }
}