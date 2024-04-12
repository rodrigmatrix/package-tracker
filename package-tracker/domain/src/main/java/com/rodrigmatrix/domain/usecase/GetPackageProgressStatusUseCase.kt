package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.UserPackage

private const val DELIVERED_MATCHER = "entregue"

class GetPackageProgressStatusUseCase {

    operator fun invoke(userPackage: UserPackage): PackageProgressStatus {
        return PackageProgressStatus(
            mailed = hasPackageMailed(userPackage),
            inProgress = isPackageInProgress(userPackage),
            delivered = hasPackageBeenDelivered(userPackage),
            outForDelivery = isPackageOutForDelivery(userPackage),
        )
    }

    private fun hasPackageMailed(userPackage: UserPackage): Boolean {
        return userPackage.postalDate.isNotEmpty()
    }

    private fun isPackageInProgress(userPackage: UserPackage): Boolean {
        return userPackage.statusUpdateList.any {
            it.title.contains("Objeto em tran")
        } || hasPackageBeenDelivered(userPackage)
    }

    private fun isPackageOutForDelivery(userPackage: UserPackage): Boolean {
        return userPackage.statusUpdateList.any {
            it.title.contains("saiu para entrega")
        } || hasPackageBeenDelivered(userPackage)
    }

    private fun hasPackageBeenDelivered(userPackage: UserPackage): Boolean {
        if (userPackage.statusUpdateList.isEmpty()) {
            return true
        }

        return userPackage.statusUpdateList.any {
            it.title.lowercase().contains(DELIVERED_MATCHER)
        }
    }
}