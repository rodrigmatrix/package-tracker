package com.rodrigmatrix.packagetracker.presentation.addpackage

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.EditPackageUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.PackageAdded
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.ShowToast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddNewPackageViewModel(
    packageId: String,
    private val addPackageUseCase: AddPackageUseCase,
    private val getPackageStatusUseCase: GetPackageStatusUseCase,
    private val editPackageUseCase: EditPackageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<AddPackageViewState, AddPackageViewEffect>(AddPackageViewState()) {

    init {
        if (packageId.isNotEmpty()) {
            setState { it.copy(isEditPackage = true) }
            loadPackage(packageId)
        }
    }

    private fun loadPackage(packageId: String) {
        viewModelScope.launch {
            getPackageStatusUseCase(packageId)
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error ->
                    setEffect { ShowToast(error.message.orEmpty()) }
                }
                .collect { userPackage ->
                    setState {
                        it.copy(
                            nameText = userPackage.name,
                            packageIdText = userPackage.packageId
                        )
                    }
                }
        }
    }
    fun editPackage() {
        viewModelScope.launch {
            val state = viewState.value

            editPackageUseCase(state.nameText, state.packageIdText)
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error ->
                    setEffect { ShowToast(error.message.orEmpty()) }
                }
                .collect {
                    setEffect {
                        PackageAdded
                    }
                }
        }
    }

    fun addPackage() {
        viewModelScope.launch {
            val state = viewState.value

            addPackageUseCase(state.nameText, state.packageIdText)
                .flowOn(dispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .onCompletion { setState { it.copy(isLoading = false) } }
                .catch { error ->
                    setEffect { ShowToast(error.message.orEmpty()) }
                }
                .collect { setEffect { PackageAdded } }
        }
    }

    fun onNameChanged(value: String) {
        setState { it.copy(nameText = value) }
    }

    fun onPackageIdChanged(value: String) {
        setState { it.copy(packageIdText = value) }
    }
}