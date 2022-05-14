package com.rodrigmatrix.packagetracker.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.packagetracker.R

enum class HomeRoutes(
    val route: String,
    @StringRes val resourceId: Int,
    val image: ImageVector
) {
    Packages("home", R.string.packages, Icons.Filled.LocalShipping),
    Settings("settings", R.string.settings, Icons.Filled.Settings),
    About("about", R.string.about, Icons.Filled.Info)
}

data class PackageDetails(
    val route: String = "package/{packageId}",
    @StringRes val resourceId: Int = R.string.details
)