package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.packagetracker.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val image: ImageVector
) {
    object Packages : Screen("home", R.string.packages, Icons.Filled.LocalShipping)
    object Settings : Screen("settings", R.string.settings, Icons.Filled.Settings)
    object PackageDetails : Screen("package/{packageId}", R.string.details, Icons.Filled.Construction)
    object About : Screen("about", R.string.about, Icons.Filled.Info)
}