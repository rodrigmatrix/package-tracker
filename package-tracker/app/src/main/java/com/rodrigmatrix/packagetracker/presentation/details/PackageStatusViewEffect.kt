package com.rodrigmatrix.packagetracker.presentation.details

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class PackageStatusViewEffect : ViewEffect {

    data class Toast(val message: String): PackageStatusViewEffect()

    object Close: PackageStatusViewEffect()
}