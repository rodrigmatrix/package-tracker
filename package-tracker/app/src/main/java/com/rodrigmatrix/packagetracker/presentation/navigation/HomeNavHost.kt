package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodrigmatrix.packagetracker.presentation.about.AboutScreen
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.packagetracker.presentation.details.DetailsScreen
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesScreen
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsScreen

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    fragmentManager: FragmentManager
) {
    NavHost(
        navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            PackagesScreen(
                navHostController,
                onAddPackageClick = {
                    AddNewPackageBottomSheetFragment()
                        .show(fragmentManager, "")
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.Packages.route) { backStackEntry ->
            DetailsScreen(
                backStackEntry.arguments?.getString("packageId").orEmpty(),
                navHostController,
                fragmentManager
            )
        }
        composable(Screen.About.route) {
            AboutScreen()
        }
    }
}