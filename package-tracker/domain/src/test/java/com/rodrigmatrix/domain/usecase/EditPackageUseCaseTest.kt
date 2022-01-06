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
internal class EditPackageUseCaseTest {

    private val packageRepository = mockk<PackageRepository>()
    private val useCase = EditPackageUseCase(packageRepository)

    @Test
    fun `when invoke then call repository`() {
        // Given
        val packageId = "123"
        val newName = "Cuscuz"
        every { packageRepository.editPackage(newName, packageId) } returns emptyFlow()

        // When
        val result = useCase(newName, packageId)

        // Then
        runBlockingTest {
            verify { packageRepository.editPackage(newName, packageId) }
            result.test {
                awaitComplete()
            }
        }
    }
}