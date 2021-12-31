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
}