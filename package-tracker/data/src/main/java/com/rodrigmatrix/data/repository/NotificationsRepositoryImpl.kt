package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.model.notification.PackageTrackerNotificationChannel
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.domain.repository.NotificationsRepository

class NotificationsRepositoryImpl(
    private val notificationService: NotificationService
): NotificationsRepository {

    override fun sendPackageUpdateNotification(title: String, description: String) {
        notificationService.sendNotification(
            title = title,
            description = description,
            R.drawable.ic_notification,
            PackageTrackerNotificationChannel.PACKAGE_UPDATES.id
        )
    }
}