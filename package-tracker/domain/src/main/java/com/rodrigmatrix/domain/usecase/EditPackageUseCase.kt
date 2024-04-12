package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.AddPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class EditPackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(addPackage: AddPackage, packageId: String): Flow<Unit> {
        return packageRepository.editPackage(addPackage, packageId)
    }
}