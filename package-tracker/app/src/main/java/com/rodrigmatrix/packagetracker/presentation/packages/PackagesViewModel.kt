package com.rodrigmatrix.packagetracker.presentation.packages

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.FetchAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.packagetracker.presentation.packages.PackagesViewEffect.OpenPackageScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PackagesViewModel(
    private val getAllPackagesUseCase: GetAllPackagesUseCase,
    private val fetchAllPackagesUseCase: FetchAllPackagesUseCase,
    private val deletePackageUseCase: DeletePackageUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackagesViewState, PackagesViewEffect>(PackagesViewState()) {

    init {
        loadPackages()
    }

    private fun loadPackages() {
        viewModelScope.launch {
            getAllPackagesUseCase()
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { exception ->
                    setState { it.errorState(exception) }
                }
                .collect { packagesList ->
                    setState { it.successState(packagesList) }
                }
        }
    }

    fun fetchPackages() {
        viewModelScope.launch {
            fetchAllPackagesUseCase()
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { exception -> setState { it.errorState(exception) } }
                .collect()
        }
    }

    fun deletePackage(packageId: String) {
        viewModelScope.launch {
            deletePackageUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { exception -> setState { it.errorState(exception) } }
                .collect()
        }
    }

    fun openPackage(packageId: String) {
        viewModelScope.launch {
            setEffect { OpenPackageScreen(packageId) }
        }
    }
}