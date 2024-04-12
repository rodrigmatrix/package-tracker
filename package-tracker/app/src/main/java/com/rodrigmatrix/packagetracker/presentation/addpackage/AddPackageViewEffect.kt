package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed interface AddPackageViewEffect: ViewEffect {

    data object GoBack: AddPackageViewEffect

    data class ShowToast(val message: String): AddPackageViewEffect

    data object OpenImageSelector: AddPackageViewEffect
}