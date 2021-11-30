package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class AddPackageViewEffect: ViewEffect {

    object PackageAdded: AddPackageViewEffect()

    data class SetPackageData(
        val name: String,
        val packageId: String
    ): AddPackageViewEffect()
}