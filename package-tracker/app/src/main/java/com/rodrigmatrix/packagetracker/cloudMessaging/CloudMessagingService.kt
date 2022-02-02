package com.rodrigmatrix.packagetracker.cloudMessaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rodrigmatrix.domain.repository.NotificationsRepository
import org.koin.android.ext.android.inject

class CloudMessagingService: FirebaseMessagingService() {

    private val notificationsRepository by inject<NotificationsRepository>()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        handleOnlyDataPayload(
            remoteMessage.data,
            remoteMessage.notification?.title.orEmpty(),
            remoteMessage.notification?.body.orEmpty()
        )
    }

    private fun handleOnlyDataPayload(
        payload: MutableMap<String, String>,
        title: String,
        body: String
    ) {
        notificationsRepository.sendLinkNotification(
            title,
            body,
            payload["link"].orEmpty()
        )
    }
}