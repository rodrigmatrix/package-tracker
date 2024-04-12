package com.rodrigmatrix.packagetracker.presentation.addpackage

import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.data.exceptions.PackageNotFoundException
import com.rodrigmatrix.domain.entity.AddPackage
import com.rodrigmatrix.domain.repository.ImageRepository
import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.EditPackageUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.analytics.ADD_PACKAGE_BUTTON_CLICK
import com.rodrigmatrix.packagetracker.analytics.ADD_PACKAGE_REQUEST_ERROR
import com.rodrigmatrix.packagetracker.analytics.EDIT_PACKAGE_NAME_BUTTON_CLICK
import com.rodrigmatrix.packagetracker.analytics.ICON_SELECTED
import com.rodrigmatrix.packagetracker.analytics.IMAGE_SELECTED
import com.rodrigmatrix.packagetracker.analytics.PACKAGE_ADDED
import com.rodrigmatrix.packagetracker.analytics.PACKAGE_EDITED
import com.rodrigmatrix.packagetracker.presentation.utils.RegexValidators
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddNewPackageViewModel(
    packageId: String?,
    private val addPackageUseCase: AddPackageUseCase,
    private val getPackageStatusUseCase: GetPackageStatusUseCase,
    private val editPackageUseCase: EditPackageUseCase,
    private val packageTrackerAnalytics: PackageTrackerAnalytics,
    private val resourceProvider: ResourceProvider,
    private val imageRepository: ImageRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel<AddPackageViewState, AddPackageViewEffect>(AddPackageViewState()) {

    init {
        if (!packageId.isNullOrEmpty()) {
            setState {
                it.copy(
                    isEditPackage = true,
                    title = R.string.edit_package,
                    actionButton = R.string.edit,
                )
            }
            loadPackage(packageId)
        }
    }

    private fun loadPackage(packageId: String) {
        viewModelScope.launch {
            getPackageStatusUseCase(packageId)
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error -> error.handleError(R.string.error_load_package) }
                .collect { userPackage ->
                    setState {
                        it.copy(
                            isLoading = false,
                            nameText = userPackage.name,
                            packageIdText = userPackage.packageId,
                            icon = getIconOptions().find { it.type == userPackage.iconType },
                            imagePath = userPackage.imagePath,
                        )
                    }
                }
        }
    }
    fun editPackage() {
        packageTrackerAnalytics.sendEvent(EDIT_PACKAGE_NAME_BUTTON_CLICK)
        viewModelScope.launch {
            val state = viewState.value

            if (state.nameText.isEmpty()) {
                return@launch setState { it.copy(nameError = R.string.package_name_empty_error) }
            }

            editPackageUseCase(state.toAddPackage(), state.packageIdText)
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error -> error.handleError() }
                .collect {
                    if (state.icon != null) {
                        state.imagePath?.let { imageRepository.deleteImage(it) }
                    }
                    packageTrackerAnalytics.sendEvent(PACKAGE_EDITED)
                    setEffect { AddPackageViewEffect.GoBack }
                }
        }
    }

    fun addPackage() {
        packageTrackerAnalytics.sendEvent(ADD_PACKAGE_BUTTON_CLICK)
        viewModelScope.launch {
            val packageIdRegex = Regex(RegexValidators.PACKAGE_ID_REGEX)
            val state = viewState.value

            if (!packageIdRegex.matches(state.packageIdText.uppercase())) {
                return@launch setState { it.copy(packageIdError = R.string.package_id_regex_error) }
            }

            if (state.nameText.isEmpty()) {
                return@launch setState { it.copy(nameError = R.string.package_name_empty_error) }
            }

            addPackageUseCase(state.toAddPackage())
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error ->
                    error.handleError(R.string.error_add_package)
                }
                .collect {
                    packageTrackerAnalytics.sendEvent(PACKAGE_ADDED)
                    setEffect { AddPackageViewEffect.GoBack }
                }
        }
    }

    fun onNameChanged(value: String) {
        setState { it.copy(nameText = value, nameError = null) }
    }

    fun onPackageIdChanged(value: String) {
        if (value.length > 13) {
            return
        }
        val lastChar: Char = value.lastOrNull() ?: ' '
        when {
            value.length in 3..11 && lastChar.isLetter() -> return
            value.length !in 3..11 && lastChar.isDigit() -> return
        }
        val keyboardType = when (value.length) {
            in 2..10 -> KeyboardType.Number
            else -> KeyboardType.Text
        }
        setState {
            it.copy(
                packageIdText = value.uppercase(),
                packageIdError = null,
                keyboardOptions = it.keyboardOptions.copy(keyboardType = keyboardType),
            )
        }
    }

    fun onChangeDialogState(dialogState: AddPackageDialogState) {
        setState { it.copy(dialogState = dialogState) }
    }

    fun onChoseImageOptionSelected(type: ChooseImageType) {
        when (type) {
            ChooseImageType.Icon -> {
                setState { it.copy(dialogState = AddPackageDialogState.ChooseIconDialog) }
            }
            ChooseImageType.Image -> {
                setState { it.copy(dialogState = AddPackageDialogState.Empty) }
                setEffect { AddPackageViewEffect.OpenImageSelector }
            }
        }
    }

    fun onIconSelected(iconOption: IconOption) {
        setState { state ->
            packageTrackerAnalytics.sendEvent(ICON_SELECTED)
            state.copy(
                dialogState = AddPackageDialogState.Empty,
                icon = iconOption,
                imagePath = null,
            )
        }
    }

    fun onImageSelected(uri: Uri?) {
        uri?.let {
            packageTrackerAnalytics.sendEvent(IMAGE_SELECTED)
            setState { state ->
                state.copy(
                    imagePath = imageRepository.saveImage(it),
                    icon = null,
                )
            }
        }
    }

    private fun Throwable.handleError(@StringRes defaultError: Int = R.string.error_edit_package) {
        Log.e("error", this.message.orEmpty())
        packageTrackerAnalytics.sendEvent(
            ADD_PACKAGE_REQUEST_ERROR,
            mapOf(
                "error" to this.message.toString()
            )
        )
        val error = when (this) {
            is PackageNotFoundException -> this.error
            else -> resourceProvider.getString(defaultError)
        }
        setEffect { AddPackageViewEffect.ShowToast(this.message.toString()) }
    }

    private fun AddPackageViewState.toAddPackage() = AddPackage(
        name = nameText,
        packageId = packageIdText,
        imagePath = imagePath,
        iconType = icon?.type,
    )
}