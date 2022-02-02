package com.rodrigmatrix.data.util

import androidx.annotation.DrawableRes

interface NotificationService {

    fun createAllNotificationChannel()

    fun sendNotification(
        title: String,
        description: String,
        @DrawableRes icon: Int,
        notificationChannel: String
    )

    fun sendLinkNotification(
        title: String,
        description: String,
        link: String,
        @DrawableRes icon: Int,
        notificationChannel: String
    )
}