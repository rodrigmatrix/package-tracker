package com.rodrigmatrix.core.viewmodel

import androidx.lifecycle.ViewModel as AndroidxViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModel<S: ViewState, E: ViewEffect?>(initialState: S): AndroidxViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState

    private val _viewEffect = MutableStateFlow<E?>(null)
    val viewEffect: StateFlow<E?> = _viewEffect

    protected fun setState(newState: () -> S) {
        _viewState.value = newState()
    }

    protected fun setEffect(newEffect: () -> E) {
        _viewEffect.value = newEffect()
    }


}