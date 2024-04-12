package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.AddPackage
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddPackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(addPackage: AddPackage): Flow<UserPackage> {
        return packageRepository.addPackage(addPackage)
    }
}