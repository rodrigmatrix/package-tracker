package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.stubs.PackageTestStubs.deliveredPackage
import com.rodrigmatrix.stubs.PackageTestStubs.emptyPackage
import com.rodrigmatrix.stubs.PackageTestStubs.inProgressPackage
import com.rodrigmatrix.stubs.PackageTestStubs.outForDeliveryPackage
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class SendPackageUpdatesNotificationsUseCaseTest {

    private val getAllPackagesUseCase = mockk<GetAllPackagesUseCase>()
    private val fetchAllPackagesUseCase = mockk<FetchAllPackagesUseCase>()
    private val getPackageProgressStatus = GetPackageProgressStatus()
    private val sendNotificationUseCase = mockk<SendPackageUpdateNotificationUseCase>(
        relaxed = true
    )
    private val useCase = SendPackageUpdatesNotificationsUseCase(
        getAllPackagesUseCase,
        fetchAllPackagesUseCase,
        getPackageProgressStatus,
        sendNotificationUseCase
    )

    @Test
    fun `given package with update Then notify about it`() {
        // Given
        val cachedPackages = listOf(outForDeliveryPackage)
        val remotePackages = listOf(deliveredPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
            // When
            useCase()

            // Then
            verify {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Objeto entregue ao destinatario - 22/07/2022"
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

        runBlockingTest {
            // When
            useCase()

            // Then
            verify {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Objeto entregue ao destinatario - 22/07/2022"
                )
            }
            verify(exactly = 1) {
                sendNotificationUseCase(
                    title = any(),
                    description = any()
                )
            }
        }
    }

    @Test
    fun `given empty remote package Then do not send any notification`() {
        // Given
        val cachedPackages = listOf(inProgressPackage)
        val remotePackages = listOf(emptyPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
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
    fun `given empty local package Then do not send any notification`() {
        // Given
        val cachedPackages = listOf(emptyPackage)
        val remotePackages = listOf(inProgressPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
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
    fun `given both empty packages Then do not send any notification`() {
        // Given
        val cachedPackages = listOf(emptyPackage)
        val remotePackages = listOf(emptyPackage)

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
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