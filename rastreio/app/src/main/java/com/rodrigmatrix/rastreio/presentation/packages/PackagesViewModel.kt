package com.rodrigmatrix.rastreio.presentation.packages

import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.rodrigmatrix.core.viewmodel.ViewEffect
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                .onStart {
                    setState {
                        viewState.value.copy(
                            isRefreshing = SwipeRefreshState(isRefreshing = true)
                        )
                    }
                    if (forceUpdate){
                        delay(2000)
                    }
                }
                .catch { exception ->
                    setState {
                        viewState.value.copy(
                            isRefreshing = SwipeRefreshState(isRefreshing = false),
                            exception = exception
                        )
                    }
                }
                .collect { packagesList ->
                    setState {
                        viewState.value.copy(
                            isRefreshing = SwipeRefreshState(isRefreshing = false),
                            exception = null,
                            packagesList = packagesList
                        )
                    }
                }
        }
    }

}

data class PackagesViewState(
    val isRefreshing: SwipeRefreshState = SwipeRefreshState(isRefreshing = false),
    val packagesList: List<UserPackageAndUpdates> = emptyList(),
    val exception: Throwable? = null
): ViewState

sealed class PackagesViewEffect: ViewEffect {

}