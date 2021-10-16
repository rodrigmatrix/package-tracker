package com.rodrigmatrix.rastreio.presentation.addpackage

import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.core.viewmodel.ViewModel
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddNewPackageViewModel(
    private val addPackageUseCase: AddPackageUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel<AddPackageViewState, AddPackageViewEffect>(AddPackageViewState()) {

    fun addPackage(name: String, packageId: String) {
        viewModelScope.launch {
            addPackageUseCase(name, packageId)
                .flowOn(dispatcher)
                .onStart { setState { it.loadingState() } }
                .catch { error -> setState { it.errorState(error.message.orEmpty()) } }
                .collect {  }
        }
    }
}