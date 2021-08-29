package com.rodrigmatrix.rastreio.presentation.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rodrigmatrix.rastreio.R
import com.rodrigmatrix.rastreio.presentation.details.DetailsScreen
import com.rodrigmatrix.rastreio.presentation.packages.PackagesScreen

fun NavGraphBuilder.addHomeGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.PACKAGES.route) { from ->
        PackagesScreen()
    }
    composable(HomeSections.SETTINGS.route) { from ->
        DetailsScreen("")
    }
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    PACKAGES(R.string.packages, Icons.Outlined.Home, "home/packages"),
    SETTINGS(R.string.settings, Icons.Outlined.Settings, "home/settings")
}