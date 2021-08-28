package com.rodrigmatrix.rastreio.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.rodrigmatrix.rastreio.presentation.home.HomeSections
import com.rodrigmatrix.rastreio.presentation.home.addHomeGraph

object MainDestinations {
    const val PACKAGES_ROUTE = "packages"
    const val SETTINGS_ROUTE = "settings"
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
        navigation(
            route = MainDestinations.PACKAGES_ROUTE,
            startDestination = HomeSections.PACKAGES.route
        ) {
            addHomeGraph(
                onSnackSelected = { snackId: Long, from: NavBackStackEntry ->
                    // In order to discard duplicated navigation events, we check the Lifecycle
                    if (from.lifecycleIsResumed()) {
                        navController.navigate("${MainDestinations.PACKAGES_ROUTE}/$snackId")
                    }
                },
                modifier = modifier
            )
        }
        navigation(
            route = MainDestinations.SETTINGS_ROUTE,
            startDestination = HomeSections.SETTINGS.route
        ) {
            addHomeGraph(
                onSnackSelected = { snackId: Long, from: NavBackStackEntry ->
                    // In order to discard duplicated navigation events, we check the Lifecycle
                    if (from.lifecycleIsResumed()) {
                        navController.navigate("${MainDestinations.SETTINGS_ROUTE}/$snackId")
                    }
                },
                modifier = modifier
            )
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED