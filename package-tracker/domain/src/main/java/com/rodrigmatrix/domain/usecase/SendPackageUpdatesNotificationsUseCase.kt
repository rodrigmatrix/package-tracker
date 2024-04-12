package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics
import kotlinx.coroutines.flow.first

class SendPackageUpdatesNotificationsUseCase(
    private val getAllPackagesUseCase: GetAllPackagesUseCase,
    private val fetchAllPackagesUseCase: FetchAllPackagesUseCase,
    private val sendNotificationUseCase: SendPackageUpdateNotificationUseCase,
    private val packageTrackerAnalytics: PackageTrackerAnalytics,
) {

    suspend operator fun invoke() {
        val cachedPackages = getAllPackagesUseCase().first()
        val updatedPackages = fetchAllPackagesUseCase().first()

        cachedPackages
            .filterNot { it.status.delivered }
            .map { userPackage ->
                updatedPackages.find {
                    it.packageId == userPackage.packageId
                }?.let { updatedPackage ->
                    getNotifications(
                        userPackage,
                        updatedPackage
                    ).firstOrNull()?.run {
                        sendNotification(updatedPackage, statusUpdate = this)
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

        return updatedPackage.statusUpdateList.plus(cachedPackage.statusUpdateList)
            .groupBy { it.title + it.date }
            .filter { it.value.size == 1 }
            .flatMap { it.value }
    }

    private fun sendNotification(userPackage: UserPackage, statusUpdate: StatusUpdate) {
        sendNotificationUseCase(
            title = userPackage.name,
            description = statusUpdate.title
        )
        packageTrackerAnalytics.sendEvent(
            "NOTIFICATION_SENT",
            mapOf(
                "title" to userPackage.name,
                "description" to statusUpdate.title
            ),
        )
    }
}