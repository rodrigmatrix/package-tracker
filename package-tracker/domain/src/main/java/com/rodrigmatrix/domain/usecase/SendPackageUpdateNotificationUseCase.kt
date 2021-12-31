package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.NotificationsRepository

class SendPackageUpdateNotificationUseCase(
    private val notificationsRepository: NotificationsRepository
) {

    operator fun invoke(title: String, description: String) {
        notificationsRepository.sendPackageUpdateNotification(title, description)
    }
}
