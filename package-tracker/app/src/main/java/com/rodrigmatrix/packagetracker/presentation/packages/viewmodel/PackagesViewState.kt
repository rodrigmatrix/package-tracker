package com.rodrigmatrix.packagetracker.presentation.packages.viewmodel

import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.packages.model.PackagesFilter

data class PackagesViewState(
    val isRefreshing: Boolean = true,
    val packagesList: List<UserPackage> = emptyList(),
    val packagesListFilter: PackagesFilter = PackagesFilter.ALL,
    val hasPackages: Boolean = false,
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

    fun successState(hasPackages: Boolean, packagesList: List<UserPackage>): PackagesViewState {
        return this.copy(
            hasPackages = hasPackages,
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