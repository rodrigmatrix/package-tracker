package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class AddPackageViewEffect: ViewEffect {

    object PackageAdded: AddPackageViewEffect()

    data class ShowToast(val message: String): AddPackageViewEffect()
}