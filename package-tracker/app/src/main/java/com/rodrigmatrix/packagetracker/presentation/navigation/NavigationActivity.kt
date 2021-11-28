package com.rodrigmatrix.packagetracker.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageViewModel
import com.rodrigmatrix.packagetracker.presentation.details.DetailsScreen
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesScreen
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsScreen
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalMaterial3Api
class NavigationActivity : AppCompatActivity() {

    private val addPackageViewModel by viewModel<AddNewPackageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PackageTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.route?.contains("package")?.not() == true) {
                            NavigationBar {
                                listOf(
                                    Screen.Home,
                                    Screen.Settings
                                ).forEach { screen ->
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
                ) {
                    NavHost(
                        navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            PackagesScreen(
                                navController,
                                onAddPackageClick = {
                                    AddNewPackageBottomSheetFragment()
                                        .show(supportFragmentManager, "")
                                }
                            )
                        }
                        composable(Screen.Settings.route) { SettingsScreen(navController) }
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