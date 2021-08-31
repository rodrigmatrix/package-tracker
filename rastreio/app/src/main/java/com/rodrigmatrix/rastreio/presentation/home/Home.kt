package com.rodrigmatrix.rastreio.presentation.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.rastreio.R

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    PACKAGES(R.string.packages, Icons.Outlined.Home, "packages"),
    SETTINGS(R.string.settings, Icons.Outlined.Settings, "settings")
}