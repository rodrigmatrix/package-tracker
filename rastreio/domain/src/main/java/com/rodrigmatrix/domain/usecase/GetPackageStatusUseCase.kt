package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class GetPackageStatusUseCase(
    private val packageRepository: PackageRepository
) {

    suspend operator fun invoke(packageId: String): Flow<UserPackage> {
        return packageRepository.getStatus(packageId)
    }
}