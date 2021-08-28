package com.rodrigmatrix.rastreio.presentation.packages

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewEffect
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PackagesViewModel(
    private val getAllPackagesUseCase: GetAllPackagesUseCase,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel<PackagesViewState, PackagesViewEffect>(PackagesViewState()) {

    init {
        loadPackages()
    }

    fun loadPackages() {
        viewModelScope.launch {
            getAllPackagesUseCase()
                .flowOn(coroutineDispatcher)
                .onStart { setState { PackagesViewState(isLoading = true) } }
                .catch { exception ->
                    setState {
                        viewState.value.copy(isLoading = false, exception = exception)
                    }
                }
                .collect { packagesList ->
                    setState {
                        viewState.value.copy(
                            isLoading = false,
                            exception = null,
                            packagesList = packagesList)
                    }
                }
        }
    }

}

data class PackagesViewState(
    val isLoading: Boolean = false,
    val packagesList: List<UserPackageAndUpdates> = emptyList(),
    val exception: Throwable? = null
): ViewState

sealed class PackagesViewEffect: ViewEffect {

}