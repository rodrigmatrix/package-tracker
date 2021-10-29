package com.rodrigmatrix.rastreio.presentation.details

import com.rodrigmatrix.core.viewmodel.ViewEffect

sealed class PackageStatusViewEffect : ViewEffect {

    data class ShowErrorSnackBarWithRetry(val message: String): PackageStatusViewEffect()
}