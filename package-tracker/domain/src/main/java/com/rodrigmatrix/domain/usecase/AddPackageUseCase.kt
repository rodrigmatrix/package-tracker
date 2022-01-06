package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddPackageUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(name: String, packageId: String): Flow<UserPackage> {
        if (name.isEmpty()) {
            return flow { throw Exception("Digite o nome da encomenda") }
        }

        if (packageId.isEmpty()) {
            return flow { throw Exception("Digite o código da encomenda") }
        }

        return packageRepository.addPackage(name, packageId)
    }
}