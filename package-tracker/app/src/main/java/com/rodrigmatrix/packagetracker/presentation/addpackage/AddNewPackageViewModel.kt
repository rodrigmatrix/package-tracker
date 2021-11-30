package com.rodrigmatrix.packagetracker.presentation.addpackage

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.EditPackageUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.PackageAdded
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.SetPackageData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
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
                .onStart { setState { it.loadingState() } }
                .catch { error ->
                    setState { it.errorState(error.message.orEmpty()) }
                }
                .collect {
                    setEffect {
                        SetPackageData(it.name, it.packageId)
                    }
                }
        }
    }
    fun editPackage(name: String, packageId: String) {
        viewModelScope.launch {
            editPackageUseCase(name, packageId)
                .flowOn(dispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { error ->
                    setState { it.errorState(error.message.orEmpty()) }
                }
                .collect {
                    setEffect {
                        PackageAdded
                    }
                }
        }
    }

    fun addPackage(name: String, packageId: String) {
        viewModelScope.launch {
            addPackageUseCase(name, packageId)
                .flowOn(dispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { error ->
                    setState { it.errorState(error.message.orEmpty()) }
                }
                .collect { setEffect { PackageAdded } }
        }
    }
}