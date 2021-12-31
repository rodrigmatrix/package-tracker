package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
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
        val cachedPackages = listOf(
            UserPackage(
            "H6XAJ123BN12",
            "Google Pixel 4",
            "",
            "20/07/2022",
            listOf(
                StatusUpdate(
                    date = "22/07/2022",
                    description = "Saiu para entrega",
                    from = StatusAddress()
                )
            )
        )
        )
        val remotePackages = listOf(
            UserPackage(
            "H6XAJ123BN12",
            "Google Pixel 4",
            "",
            "20/07/2022",
            listOf(
                StatusUpdate(
                    date = "22/07/2022",
                    description = "Entregue",
                    from = StatusAddress()
                ),
                StatusUpdate(
                    date = "22/07/2022",
                    description = "Saiu para entrega",
                    from = StatusAddress()
                )
            )
        )
        )

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
            // When
            useCase()

            // Then
            verify {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Entregue - 22/07/2022"
                )
            }
        }
    }

    @Test
    fun `given package with 2 updates Then notify about the last one`() {
        // Given
        val cachedPackages = listOf(
            UserPackage(
                "H6XAJ123BN12",
                "Google Pixel 4",
                "",
                "20/07/2022",
                listOf(
                    StatusUpdate(
                        date = "22/07/2022",
                        description = "Em progresso",
                        from = StatusAddress()
                    )
                )
            )
        )
        val remotePackages = listOf(
            UserPackage(
                "H6XAJ123BN12",
                "Google Pixel 4",
                "",
                "20/07/2022",
                listOf(
                    StatusUpdate(
                        date = "22/07/2022",
                        description = "Entregue",
                        from = StatusAddress()
                    ),
                    StatusUpdate(
                        date = "22/07/2022",
                        description = "Saiu para entrega",
                        from = StatusAddress()
                    ),
                    StatusUpdate(
                        date = "22/07/2022",
                        description = "Em progresso",
                        from = StatusAddress()
                    )
                )
            )
        )

        coEvery { getAllPackagesUseCase() } returns flow { emit(cachedPackages) }
        coEvery { fetchAllPackagesUseCase() } returns flow { emit(remotePackages) }

        runBlockingTest {
            // When
            useCase()

            // Then
            verify {
                sendNotificationUseCase(
                    title = "Google Pixel 4",
                    description = "Entregue - 22/07/2022"
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
}