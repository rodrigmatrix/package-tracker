package com.rodrigmatrix.rastreio.presentation.navigation

import androidx.annotation.StringRes
import com.rodrigmatrix.rastreio.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,

) {
    object Home : Screen("home", R.string.home)
    object Settings : Screen("settings", R.string.settings)
    object Packages : Screen("package/{packageId}", R.string.packages)
}