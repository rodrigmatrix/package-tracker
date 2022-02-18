package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.analytics.PACKAGE_NOTIFICATION_SENT
import com.rodrigmatrix.data.analytics.PackageTrackerAnalytics
import com.rodrigmatrix.data.model.notification.PackageTrackerNotificationChannel
import com.rodrigmatrix.data.util.NotificationService
import com.rodrigmatrix.domain.repository.NotificationsRepository

class NotificationsRepositoryImpl(
    private val notificationService: NotificationService,
    private val packageTrackerAnalytics: PackageTrackerAnalytics
): NotificationsRepository {

    override fun sendPackageUpdateNotification(title: String, description: String) {
        notificationService.sendNotification(
            title = title,
            description = description,
            R.drawable.ic_notification,
            PackageTrackerNotificationChannel.PACKAGE_UPDATES.id
        )
        packageTrackerAnalytics.sendEvent(PACKAGE_NOTIFICATION_SENT)
    }

    override fun sendLinkNotification(title: String, description: String, link: String) {
        notificationService.sendLinkNotification(
            title = title,
            description = description,
            link = link,
            R.drawable.ic_notification,
            PackageTrackerNotificationChannel.PACKAGE_UPDATES.id
        )
        packageTrackerAnalytics.sendEvent(PACKAGE_NOTIFICATION_SENT)
    }
}