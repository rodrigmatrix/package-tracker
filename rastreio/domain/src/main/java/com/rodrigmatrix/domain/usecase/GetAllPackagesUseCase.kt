package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class GetAllPackagesUseCase(
    private val packageRepository: PackageRepository
) {

    suspend operator fun invoke(): Flow<List<UserPackageAndUpdates>> {
        return packageRepository.getAllPackages()
    }

}