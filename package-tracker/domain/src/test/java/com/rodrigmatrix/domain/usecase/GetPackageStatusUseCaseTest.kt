package com.rodrigmatrix.domain.usecase

import app.cash.turbine.test
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.stubs.deliveredPackage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetPackageStatusUseCaseTest {

    private val packageRepository = mockk<PackageRepository>()
    private val useCase = GetPackageStatusUseCase(packageRepository)

    @Test
    fun `when invoke then call repository`() {
        // Given
        val packageId = "123"
        every { packageRepository.getStatus(packageId) } returns flow { emit(deliveredPackage) }

        // When
        val result = useCase(packageId)

        // Then
        runBlockingTest {

            verify { packageRepository.getStatus(packageId) }
            result.test {
                assertEquals(deliveredPackage, expectMostRecentItem())
            }
        }
    }
}