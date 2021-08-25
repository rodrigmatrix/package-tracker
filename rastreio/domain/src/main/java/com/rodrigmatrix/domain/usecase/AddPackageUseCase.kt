package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class AddPackageUseCase(
    private val packageRepository: PackageRepository
) {

    suspend operator fun invoke(packageId: String): Flow<UserPackageAndUpdates> {
        return packageRepository.addPackage(packageId)
    }

}