package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.NotificationsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class SendPackageUpdateNotificationUseCaseTest {


    private val notificationsRepository = mockk<NotificationsRepository>()
    private val useCase = SendPackageUpdateNotificationUseCase(notificationsRepository)

    @Test
    fun `when invoke then call repository`() {
        // Given
        val title = "Cuscuz"
        val message = "Chegou"
        every {
            notificationsRepository.sendPackageUpdateNotification(title, message)
        } returns Unit

        // When
        val result = useCase(title, message)

        // Then
        verify { notificationsRepository.sendPackageUpdateNotification(title, message) }
    }
}