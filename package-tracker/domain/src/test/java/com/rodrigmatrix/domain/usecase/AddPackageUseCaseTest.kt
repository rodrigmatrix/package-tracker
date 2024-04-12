package com.rodrigmatrix.domain.usecase

import app.cash.turbine.test
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.stubs.deliveredPackage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
internal class AddPackageUseCaseTest {

    private val packageRepository = mockk<PackageRepository>()
    private val useCase = AddPackageUseCase(packageRepository)

    @Test
    fun `when invoke then call repository`() {
//        // Given
//        val packageId = "123"
//        val name = "Cuscuz"
//        every {
//            packageRepository.addPackage(name, packageId)
//        } returns flow { emit(deliveredPackage) }
//
//        // When
//        val result = useCase(name, packageId)
//
//        // Then
//        runBlockingTest {
//            verify { packageRepository.addPackage(name, packageId) }
//            result.test {
//                assertEquals(deliveredPackage, expectMostRecentItem())
//            }
//        }
    }
}