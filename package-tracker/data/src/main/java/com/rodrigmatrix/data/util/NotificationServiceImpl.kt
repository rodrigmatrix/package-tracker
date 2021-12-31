package com.rodrigmatrix.data.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.data.model.notification.PackageTrackerNotificationChannel
import kotlin.random.Random

class NotificationServiceImpl(
    private val applicationContext: Context,
    private val resourceProvider: ResourceProvider,
    private val notificationManager: NotificationManager
) : NotificationService {

    override fun createAllNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PackageTrackerNotificationChannel.values().forEach { item ->
                val channel = NotificationChannel(
                    item.id,
                    resourceProvider.getString(item.titleStringRes),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description =  resourceProvider.getString(item.descriptionStringRes)

                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    override fun sendNotification(
        title: String,
        description: String,
        @DrawableRes icon: Int,
        notificationChannel: String
    ) {
        val builder = NotificationCompat.Builder(applicationContext, notificationChannel)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText(description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(Random.nextInt(), builder)
    }
}