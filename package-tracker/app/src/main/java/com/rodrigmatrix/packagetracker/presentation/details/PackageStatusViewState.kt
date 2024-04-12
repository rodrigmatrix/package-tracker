package com.rodrigmatrix.packagetracker.presentation.details

import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.UserPackage

data class PackageStatusViewState(
    val isLoading: Boolean = true,
    val userPackage: UserPackage? = null,
    val deletePackageDialogVisible: Boolean = false
): ViewState