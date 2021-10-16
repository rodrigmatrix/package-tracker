package com.rodrigmatrix.rastreio.presentation.packages

import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.rodrigmatrix.core.viewmodel.ViewEffect
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.rastreio.presentation.packages.PackagesViewEffect.OpenPackageScreen
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

    fun loadPackages(forceUpdate: Boolean = false) {
        viewModelScope.launch {
            getAllPackagesUseCase(forceUpdate)
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

    fun openPackage(packageId: String) {
        viewModelScope.launch {
            setEffect { OpenPackageScreen(packageId) }
        }
    }

}

data class PackagesViewState(
    val isRefreshing: SwipeRefreshState = SwipeRefreshState(isRefreshing = false),
    val packagesList: List<UserPackage> = emptyList(),
    val exception: Throwable? = null
): ViewState {

    fun loadingState(): PackagesViewState {
        return this.copy(isRefreshing = SwipeRefreshState(isRefreshing = true))
    }

    fun successState(packagesList: List<UserPackage>): PackagesViewState {
        return this.copy(
            isRefreshing = SwipeRefreshState(isRefreshing = false),
            packagesList = packagesList,
            exception = null
        )
    }

    fun errorState(throwable: Throwable): PackagesViewState {
        return this.copy(
            isRefreshing = SwipeRefreshState(isRefreshing = false),
            exception = throwable
        )
    }
}

sealed class PackagesViewEffect: ViewEffect {

    data class OpenPackageScreen(val packageId: String): PackagesViewEffect()
}