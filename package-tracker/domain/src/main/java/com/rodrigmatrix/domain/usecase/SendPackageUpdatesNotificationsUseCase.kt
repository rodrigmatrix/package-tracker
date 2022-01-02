package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.first

class SendPackageUpdatesNotificationsUseCase(
    private val getAllPackagesUseCase: GetAllPackagesUseCase,
    private val fetchAllPackagesUseCase: FetchAllPackagesUseCase,
    private val getPackageProgressStatus: GetPackageProgressStatus,
    private val sendNotificationUseCase: SendPackageUpdateNotificationUseCase
) {

    suspend operator fun invoke() {
        val cachedPackages = getAllPackagesUseCase().first()
        val updatedPackages = fetchAllPackagesUseCase().first()

        cachedPackages
            .filterNot { getPackageProgressStatus(it).delivered }
            .map { userPackage ->
                updatedPackages.find {
                    it.packageId == userPackage.packageId
                }?.let { updatedPackage ->
                    getNotifications(
                        userPackage,
                        updatedPackage
                    ).firstOrNull()?.run {
                        sendNotification(userPackage, statusUpdate = this)
                    }
                }
            }
    }

    private fun getNotifications(
        cachedPackage: UserPackage,
        updatedPackage: UserPackage
    ): List<StatusUpdate> {
        if (updatedPackage.statusUpdateList.isEmpty()) {
            return emptyList()
        }

        return cachedPackage.statusUpdateList.plus(updatedPackage.statusUpdateList)
            .groupBy { it.description + it.date }
            .filter { it.value.size == 1 }
            .flatMap { it.value }
    }

    private fun sendNotification(userPackage: UserPackage, statusUpdate: StatusUpdate) {
        val description = statusUpdate.description + " - " + statusUpdate.date
        sendNotificationUseCase(userPackage.name, description)
    }
}