package com.rodrigmatrix.domain.usecase

import app.cash.turbine.test
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.stubs.packagesList
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
internal class FetchAllPackagesUseCaseTest {

    private val packageRepository = mockk<PackageRepository>()
    private val useCase = FetchAllPackagesUseCase(packageRepository)

    @Test
    fun `when invoke then call repository`() {
        // Given
        every { packageRepository.fetchPackages() } returns flow { emit(packagesList) }

        // When
        val result = useCase()

        // Then
        runBlockingTest {

            verify { packageRepository.fetchPackages() }
            result.test {
                assertEquals(packagesList, expectMostRecentItem())
            }
        }
    }
}