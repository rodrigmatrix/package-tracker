package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class EditPackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(name: String, packageId: String): Flow<Unit> {
        return packageRepository.editPackage(name, packageId)
    }
}