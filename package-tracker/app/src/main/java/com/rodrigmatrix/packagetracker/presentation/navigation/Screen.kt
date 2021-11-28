package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.packagetracker.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val image: ImageVector
) {
    object Home : Screen("home", R.string.home, Icons.Filled.Home)
    object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    object Packages : Screen("package/{packageId}", R.string.packages, Icons.Filled.Construction)
}