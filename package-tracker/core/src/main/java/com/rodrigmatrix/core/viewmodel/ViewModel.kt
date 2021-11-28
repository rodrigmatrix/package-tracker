package com.rodrigmatrix.core.viewmodel

import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.lifecycle.ViewModel as AndroidxViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class ViewModel<S: ViewState, E: ViewEffect?>(initialState: S): AndroidxViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState

    private val _viewEffect = MutableSharedFlow<E>()
    val viewEffect: SharedFlow<E> = _viewEffect

    protected fun setState(newState: (S) -> S) {
        _viewState.value = newState(viewState.value)
    }

     protected suspend fun setEffect(newEffect: () -> E) {
        _viewEffect.emit(newEffect())
    }
}