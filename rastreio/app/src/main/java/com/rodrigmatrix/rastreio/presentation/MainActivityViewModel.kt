package com.rodrigmatrix.rastreio.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val packageRepository: PackageStatusRepository
): ViewModel() {

    fun getPackageStatus(packageId: String): Flow<UserPackage> = flow {
        packageRepository.getStatus(packageId).collect {
            emit(it)
        }
    }


}