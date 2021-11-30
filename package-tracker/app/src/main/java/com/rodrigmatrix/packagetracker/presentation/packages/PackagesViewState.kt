package com.rodrigmatrix.packagetracker.presentation.packages

import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.rodrigmatrix.core.viewmodel.ViewState
import com.rodrigmatrix.domain.entity.UserPackage

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