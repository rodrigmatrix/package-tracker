package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.core.extensions.toDate
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TIMESTAMP_PATTERN = "dd/MM/yyyy HH:mm"

class GetAllPackagesUseCase(
    private val packageRepository: PackageRepository
) {

    operator fun invoke(): Flow<List<UserPackage>> {
        return packageRepository.getPackages()
            .map { packagesList ->
                packagesList.sortedByDescending { userPackage ->
                    userPackage.statusUpdateList
                        .firstOrNull()
                        ?.getTimeStamp()
                        .toDate(TIMESTAMP_PATTERN)
                        ?.time
                }
            }
    }

    private fun StatusUpdate.getTimeStamp(): String {
        return "$date $hour"
    }
}