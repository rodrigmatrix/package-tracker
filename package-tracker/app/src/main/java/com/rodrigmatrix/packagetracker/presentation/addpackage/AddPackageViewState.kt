package com.rodrigmatrix.packagetracker.presentation.addpackage

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.packagetracker.R

data class AddPackageViewState(
    @StringRes val title: Int = R.string.add_package,
    @StringRes val actionButton: Int = R.string.add,
    val isLoading: Boolean = false,
    val isEditPackage: Boolean = false,
    val nameText: String = "",
    @StringRes val nameError: Int? = null,
    val packageIdText: String = "",
    val keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Characters,
        autoCorrect = false,
    ),
    @StringRes val packageIdError: Int? = null,
    val imagePath: String? = null,
    val icon: IconOption? = null,
    val dialogState: AddPackageDialogState = AddPackageDialogState.Empty,
): ViewState

sealed interface AddPackageDialogState {
    data object Empty : AddPackageDialogState

    data object ChooseImageDialog : AddPackageDialogState

    data object ChooseIconDialog : AddPackageDialogState
}
