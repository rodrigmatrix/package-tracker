package com.rodrigmatrix.packagetracker.presentation.packages

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class PackagesViewEffect: ViewEffect {

    data class OpenPackageScreen(val packageId: String): PackagesViewEffect()
}