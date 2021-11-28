package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.UserPackage

private const val DELIVERED_MATCHER = "entregue"

class GetPackageProgressStatus {

    operator fun invoke(userPackage: UserPackage): PackageProgressStatus {
        return PackageProgressStatus(
            hasPackageMailed(userPackage),
            isPackageInProgress(userPackage),
            hasPackageBeenDelivered(userPackage)
        )
    }

    private fun hasPackageMailed(userPackage: UserPackage): Boolean {
        return userPackage.postalDate.isNotEmpty()
    }

    private fun isPackageInProgress(userPackage: UserPackage): Boolean {
        return userPackage.statusUpdateList.size > 1
    }

    private fun hasPackageBeenDelivered(userPackage: UserPackage): Boolean {
        return userPackage.statusUpdateList.any {
            it.description.lowercase().contains(DELIVERED_MATCHER)
        }
    }

}