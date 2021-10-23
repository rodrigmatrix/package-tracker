package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class DeletePackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(packageId: String): Flow<Unit> {
        return packageRepository.deletePackage(packageId)
    }
}