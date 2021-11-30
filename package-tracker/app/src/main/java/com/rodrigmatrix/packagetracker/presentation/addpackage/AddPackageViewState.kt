package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewState

data class AddPackageViewState(
    val isLoading: Boolean = false,
    val isEditPackage: Boolean = false,
    val error: String? = null
): ViewState {

    fun loadingState(): AddPackageViewState {
        return this.copy(isLoading = true)
    }

    fun errorState(error: String): AddPackageViewState {
        return this.copy(isLoading = false, error = error)
    }
}