package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
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
    windowSizeClass: WindowSizeClass,
    navHostController: NavHostController,
    fragmentManager: FragmentManager
) {
    NavHost(
        navHostController,
        startDestination = HomeRoutes.Packages.route
    ) {
        composable(HomeRoutes.Packages.route) {
            PackagesScreen(
                windowSizeClass = windowSizeClass,
                navHostController
            )
        }
        composable(HomeRoutes.Settings.route) {
            SettingsScreen()
        }
        composable(PackageDetails().route) { backStackEntry ->
            DetailsScreen(
                backStackEntry.arguments?.getString("packageId").orEmpty(),
                navHostController,
                fragmentManager
            )
        }
        composable(HomeRoutes.About.route) {
            AboutScreen()
        }
    }
}