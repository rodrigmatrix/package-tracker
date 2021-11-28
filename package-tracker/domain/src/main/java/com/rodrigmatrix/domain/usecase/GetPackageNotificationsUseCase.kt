package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

class GetPackageNotificationsUseCase {

    operator fun invoke(
        cachedPackage: UserPackage,
        updatedPackage: UserPackage
    ): List<StatusUpdate> {
        return cachedPackage.statusUpdateList.plus(updatedPackage.statusUpdateList)
            .groupBy { it.description + it.date }
            .filter { it.value.size == 1 }
            .flatMap { it.value }
    }
}