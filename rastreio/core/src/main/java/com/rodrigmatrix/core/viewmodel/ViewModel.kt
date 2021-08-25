package com.rodrigmatrix.core.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ViewModel<S: ViewState, E: ViewEffect>(initialState: S): ViewModel() {

    private val _viewState = MutableLiveData(initialState)
    val viewState: LiveData<S?> = _viewState

    private val _viewEffect = MutableLiveData<E>()
    val viewEffect: LiveData<E> = _viewEffect

    protected fun setState(newState: () -> S?) {
        _viewState.value = newState()
    }

    protected fun setEffect(newEffect: () -> E) {
        _viewEffect.value = newEffect()
    }


}