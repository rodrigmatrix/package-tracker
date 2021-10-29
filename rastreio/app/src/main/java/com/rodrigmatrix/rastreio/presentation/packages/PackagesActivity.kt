package com.rodrigmatrix.rastreio.presentation.packages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rodrigmatrix.rastreio.presentation.details.DetailsScreen
import com.rodrigmatrix.rastreio.presentation.navigation.Screen
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme

@OptIn(ExperimentalMaterial3Api::class)
class PackagesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            RastreioTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.route == "home") {
                            NavigationBar {
                                listOf(
                                    Screen.Home,
                                    Screen.Settings
                                ).forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
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
                ) {
                    NavHost(
                        navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) { PackagesScreen(navController) }
                        composable(Screen.Settings.route) { PackagesScreen(navController) }
                        composable(Screen.Packages.route) { backStackEntry ->
                            DetailsScreen(
                                backStackEntry.arguments?.getString("packageId").orEmpty(),
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}