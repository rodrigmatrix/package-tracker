package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.repository.PackageTrackerAnalytics
import com.rodrigmatrix.stubs.deliveredPackage
import com.rodrigmatrix.stubs.emptyPackage
import com.rodrigmatrix.stubs.inProgressPackage
import com.rodrigmatrix.stubs.mailedPackage
import com.rodrigmatrix.stubs.outForDeliveryPackage
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SendPackageUpdatesNotificationsUseCaseTest {

    private val getAllPackagesUseCase = mockk<GetAllPackagesUseCase>()
    private val fetchAllPackagesUseCase = mockk<FetchAllPackagesUseCase>()
    private val sendNotificationUseCase = mockk<SendPackageUpdateNotificationUseCase>(relaxed = true)
    private val analytics = mockk<PackageTrackerAnalytics>(relaxed = true)

    private val useCase = SendPackageUpdatesNotificationsUseCase(
        getAllPackagesUseCase,
        fetchAllPackagesUseCase,
        sendNotificationUseCase,
        analytics
    )

    // DELETE FROM status_update WHERE statusId = 0

    @Test
    fun `given package with update Then notify about it`() {
        // Given
        val cachedPackages = listOf(outForDeliveryPackage)
        val remotePackages = listOf(deliveredPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runTest {
            // When
            useCase()

            // Then
            verify(exactly = 1) {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Objeto entregue ao destinatario"
                )
            }
        }
    }

    @Test
    fun `given package with 2 updates Then notify about the last one`() {
        // Given
        val cachedPackages = listOf(inProgressPackage)
        val remotePackages = listOf(deliveredPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runTest {
            // When
            useCase()

            // Then
            verify(exactly = 1) {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Objeto entregue ao destinatario"
                )
            }
        }
    }

    @Test
    fun `given empty remote package Then do not send any notification`() {
        // Given
        val cachedPackages = listOf(emptyPackage)
        val remotePackages = listOf(emptyPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runTest {
            // When
            useCase()

            // Then
            verify(exactly = 0) {
                sendNotificationUseCase(
                    title = any(),
                    description = any()
                )
            }
        }
    }

    @Test
    fun `given empty local package and mailed remote package Then send notification`() {
        // Given
        val cachedPackages = listOf(emptyPackage)
        val remotePackages = listOf(mailedPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runTest {
            // When
            useCase()

            // Then
            verify(exactly = 1) {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Postado",
                )
            }
        }
    }

    @Test
    fun `given both empty packages Then do not send any notification`() {
        // Given
        val cachedPackages = listOf(emptyPackage)
        val remotePackages = listOf(emptyPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runTest {
            // When
            useCase()

            // Then
            verify(exactly = 0) {
                sendNotificationUseCase(
                    title = any(),
                    description = any()
                )
            }
        }
    }
}