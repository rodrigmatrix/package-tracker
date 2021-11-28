package com.rodrigmatrix.packagetracker.presentation.details

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatus
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.presentation.details.PackageStatusViewEffect.Toast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PackagesDetailsViewModel(
    private val getPackageStatusUseCase: GetPackageStatusUseCase,
    private val getPackageProgressStatus: GetPackageProgressStatus,
    private val deletePackageUseCase: DeletePackageUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackageStatusViewState, PackageStatusViewEffect>(PackageStatusViewState()) {

    fun getPackageStatus(packageId: String) {
        viewModelScope.launch {
            getPackageStatusUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .onStart { setState { viewState.value.copy(isLoading = true) } }
                .catch { error ->
                    setState { viewState.value.copy(isLoading = false) }
                    setEffect {
                        Toast(error.message.orEmpty())
                    }
                }
                .collect { userPackage ->
                    setState {
                        it.copy(
                            isLoading = false,
                            userPackage = userPackage,
                            packageProgressStatus = getPackageProgressStatus(userPackage)
                        )
                    }
                }
        }
    }

    fun showDeleteDialog() {
        setState { viewState.value.copy(deletePackageDialogVisible = true) }
    }

    fun hideDeleteDialog() {
        setState { viewState.value.copy(deletePackageDialogVisible = false) }
    }

    fun deletePackage(packageId: String) {
        viewModelScope.launch {
            deletePackageUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .catch { error ->
                    setState { viewState.value.copy(isLoading = false) }
                    setEffect {
                        Toast(error.message.orEmpty())
                    }
                }
                .collect {
                    setState { viewState.value.copy(deletePackageDialogVisible = false) }
                    setEffect {
                        PackageStatusViewEffect.Close
                    }
                }
        }
    }
}