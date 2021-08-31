package com.rodrigmatrix.rastreio.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rodrigmatrix.rastreio.presentation.details.DetailsScreen
import com.rodrigmatrix.rastreio.presentation.home.HomeSections
import com.rodrigmatrix.rastreio.presentation.packages.PackagesScreen

object MainDestinations {
    const val PACKAGES_ROUTE = "packages"
    const val SETTINGS_ROUTE = "settings"
    const val PACKAGE_DETAILS = "package/{packageId}"
}

@Composable
fun RastreioNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.PACKAGES_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MainDestinations.PACKAGES_ROUTE) {
            PackagesScreen(navController)
        }
        composable(MainDestinations.SETTINGS_ROUTE) {
            DetailsScreen(navController, "")
        }
        composable(MainDestinations.PACKAGE_DETAILS) { backStackEntry ->
            DetailsScreen(
                navController,
                backStackEntry.arguments?.getString("packageId").orEmpty()
            )
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED