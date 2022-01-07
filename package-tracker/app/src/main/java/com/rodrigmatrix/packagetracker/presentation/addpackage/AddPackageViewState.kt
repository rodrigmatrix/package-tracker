package com.rodrigmatrix.packagetracker.presentation.addpackage

import com.rodrigmatrix.core.viewmodel.ViewState

data class AddPackageViewState(
    val isLoading: Boolean = false,
    val isEditPackage: Boolean = false,
    val nameText: String = "",
    val packageIdText: String = ""
): ViewState