package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class GetAllPackagesUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(): Flow<List<UserPackage>> {
        return packageRepository.getPackages()
    }
}