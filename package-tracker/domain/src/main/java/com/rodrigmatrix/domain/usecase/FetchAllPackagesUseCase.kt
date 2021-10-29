package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow

class FetchAllPackagesUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(): Flow<Unit> {
        return packageRepository.fetchPackages()
    }
}