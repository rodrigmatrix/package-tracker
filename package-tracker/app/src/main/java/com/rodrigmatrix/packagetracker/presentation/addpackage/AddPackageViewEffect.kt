package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class AddPackageViewEffect: ViewEffect {

    object PackageAdded: AddPackageViewEffect()
}