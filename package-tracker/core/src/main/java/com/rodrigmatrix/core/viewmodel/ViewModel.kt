package com.rodrigmatrix.core.viewmodel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.*
import androidx.lifecycle.ViewModel as AndroidxViewModel

abstract class ViewModel<S: ViewState, E: ViewEffect?>(initialState: S): AndroidxViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState

    private val _viewEffect = Channel<E>()
    val viewEffect: Flow<E> = _viewEffect.receiveAsFlow()

    protected fun setState(newState: (S) -> S) {
        _viewState.value = newState(viewState.value)
    }

     protected suspend fun setEffect(newEffect: () -> E) {
        _viewEffect.send(newEffect())
    }
}