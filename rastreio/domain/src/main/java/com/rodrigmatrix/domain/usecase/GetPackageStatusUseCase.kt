package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.*

class GetPackageStatusUseCase(
    private val packageRepository: PackageRepository
) {

    suspend operator fun invoke(
        packageId: String,
        forceUpdate: Boolean
    ): Flow<UserPackageAndUpdates> {
        return packageRepository.getStatus(packageId, forceUpdate)
    }

}