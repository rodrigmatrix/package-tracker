package com.rodrigmatrix.packagetracker.presentation.details

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.DeletePackageUseCase
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatusUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.presentation.details.PackageStatusViewEffect.Toast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PackagesDetailsViewModel(
    private val getPackageStatusUseCase: GetPackageStatusUseCase,
    private val deletePackageUseCase: DeletePackageUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackageStatusViewState, PackageStatusViewEffect>(PackageStatusViewState()) {

    fun getPackageStatus(packageId: String) {
        viewModelScope.launch {
            getPackageStatusUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .onStart { setState { it.copy(isLoading = true) } }
                .catch { error ->
                    setState { it.copy(isLoading = false) }
                    if (error !is NullPointerException) {
                        setEffect {
                            Toast(error.message.orEmpty())
                        }
                    }
                }
                .collect { userPackage ->
                    setState {
                        it.copy(
                            isLoading = false,
                            userPackage = userPackage,
                        )
                    }
                }
        }
    }

    fun showDeleteDialog() {
        setState { it.copy(deletePackageDialogVisible = true) }
    }

    fun hideDeleteDialog() {
        setState { it.copy(deletePackageDialogVisible = false) }
    }

    fun deletePackage(packageId: String) {
        viewModelScope.launch {
            deletePackageUseCase(packageId)
                .flowOn(coroutineDispatcher)
                .catch { error ->
                    setState { it.copy(isLoading = false) }
                    setEffect {
                        Toast(error.message.orEmpty())
                    }
                }
                .onCompletion {
                    setState { it.copy(deletePackageDialogVisible = false) }
                    setEffect {
                        PackageStatusViewEffect.Close
                    }
                }
                .collect()
        }
    }
}