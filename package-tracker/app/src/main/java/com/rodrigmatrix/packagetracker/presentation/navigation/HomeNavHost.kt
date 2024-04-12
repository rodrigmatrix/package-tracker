package com.rodrigmatrix.packagetracker.presentation.navigation

import android.Manifest
import android.os.Build
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.rodrigmatrix.packagetracker.presentation.about.AboutScreen
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageScreen
import com.rodrigmatrix.packagetracker.presentation.details.DetailsScreen
import com.rodrigmatrix.packagetracker.presentation.notification.NotificationPermissionScreen
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesScreen
import com.rodrigmatrix.packagetracker.presentation.settings.SettingsScreen
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenContentType
import com.rodrigmatrix.packagetracker.presentation.utils.ScreenNavigationType

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeNavHost(
    navigationType: ScreenNavigationType,
    contentType: ScreenContentType,
    navHostController: NavHostController,
    fragmentManager: FragmentManager
) {
    NavHost(
        navHostController,
        startDestination = HomeRoutes.Packages.route
    ) {
        composable(HomeRoutes.Packages.route) {
            PackagesScreen(
                navigationType,
                contentType,
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
        composable(NavigationRoutes.NOTIFICATION_DIALOG_ROUTE) {
            if (Build.VERSION.SDK_INT >= 33) {
                NotificationPermissionScreen(
                    permissionRequest = rememberPermissionState(
                        permission = Manifest.permission.POST_NOTIFICATIONS
                    ),
                    navController = navHostController
                )
            }
        }

        composable(
            "add_package?packageId={packageId}",
            arguments = listOf(navArgument("packageId") { defaultValue = "" }),
        ) { backStackEntry ->
            AddPackageScreen(
                navController = navHostController,
                packageId = backStackEntry.arguments?.getString("packageId").orEmpty(),
            )
        }
    }
}