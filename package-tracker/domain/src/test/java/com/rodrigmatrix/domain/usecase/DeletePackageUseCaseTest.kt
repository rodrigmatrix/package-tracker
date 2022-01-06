package com.rodrigmatrix.domain.usecase

import app.cash.turbine.test
import com.rodrigmatrix.domain.repository.PackageRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class DeletePackageUseCaseTest {

    private val packageRepository = mockk<PackageRepository>()
    private val useCase = DeletePackageUseCase(packageRepository)

    @Test
    fun `when invoke then call repository`() {
        // Given
        val packageId = "123"
        every { packageRepository.deletePackage(packageId) } returns emptyFlow()

        // When
        val result = useCase(packageId)

        // Then
        runBlockingTest {
            verify { packageRepository.deletePackage(packageId) }
            result.test {
                awaitComplete()
            }
        }
    }
}