package com.rodrigmatrix.packagetracker.presentation.packages

import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackage

data class PackagesViewState(
    val isRefreshing: Boolean = true,
    val packagesList: List<UserPackage> = emptyList(),
    val deletePackageDialogVisible: Boolean = false,
    val selectedPackageId: String = "",
    val exception: Throwable? = null
): ViewState {

    fun loadingState(): PackagesViewState {
        return this.copy(isRefreshing = true)
    }

    fun showDialogDeleteState(packageId: String): PackagesViewState {
        return this.copy(deletePackageDialogVisible = true, selectedPackageId = packageId)
    }

    fun hideDialogDelete(): PackagesViewState {
        return this.copy(deletePackageDialogVisible = false, selectedPackageId = "")
    }

    fun successState(packagesList: List<UserPackage>): PackagesViewState {
        return this.copy(
            isRefreshing = false,
            packagesList = packagesList,
            deletePackageDialogVisible = false,
            selectedPackageId = "",
            exception = null
        )
    }

    fun errorState(throwable: Throwable): PackagesViewState {
        return this.copy(
            isRefreshing = false,
            selectedPackageId = "",
            exception = throwable
        )
    }
}