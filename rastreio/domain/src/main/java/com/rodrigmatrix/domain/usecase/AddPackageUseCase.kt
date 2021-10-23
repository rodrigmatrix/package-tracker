package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class AddPackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(name: String, packageId: String): Flow<UserPackage> {
        return packageRepository.addPackage(name, packageId)
    }
}