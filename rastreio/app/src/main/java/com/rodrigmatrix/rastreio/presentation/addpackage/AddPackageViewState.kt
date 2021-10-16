package com.rodrigmatrix.rastreio.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewState

data class AddPackageViewState(
    val isLoading: Boolean = false,
    val error: String = ""
): ViewState {

    fun loadingState(): AddPackageViewState {
        return this.copy(isLoading = true)
    }

    fun errorState(error: String): AddPackageViewState {
        return this.copy(isLoading = false, error = error)
    }
}