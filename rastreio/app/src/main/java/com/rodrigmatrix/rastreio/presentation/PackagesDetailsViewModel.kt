package com.rodrigmatrix.rastreio.presentation

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewEffect
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PackagesDetailsViewModel(
    private val getPackageStatusUseCase: GetPackageStatusUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackageStatusViewState, PackageStatusViewEffect>(PackageStatusViewState()) {

    init {
        getPackageStatus("OP212763677BR")
    }

    fun getPackageStatus(packageId: String) {
        viewModelScope.launch {
            getPackageStatusUseCase(packageId, false)
                .flowOn(coroutineDispatcher)
                .catch { error ->
                    setState { viewState.value?.copy(isLoading = false) }
                    setEffect { PackageStatusViewEffect.ShowSnackbar(error.message.orEmpty()) }
                }
                .collect { userPackage ->
                    setState { viewState.value?.copy(isLoading = false, userPackage = userPackage) }
                }
        }
    }

}

data class PackageStatusViewState(
    val isLoading: Boolean = true,
    val userPackage: UserPackageAndUpdates? = null
): ViewState

sealed class PackageStatusViewEffect : ViewEffect {

    data class ShowSnackbar(val message: String): PackageStatusViewEffect()
}