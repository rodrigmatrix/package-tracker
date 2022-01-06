package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.stubs.deliveredPackage
import com.rodrigmatrix.stubs.deliveredProgressStatus
import com.rodrigmatrix.stubs.inProgressPackage
import com.rodrigmatrix.stubs.inProgressStatus
import com.rodrigmatrix.stubs.mailedPackage
import com.rodrigmatrix.stubs.mailedProgressStatus
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetPackageProgressStatusTest {

    private val useCase = GetPackageProgressStatusUseCase()

    @Test
    fun `invoke with mailed package Should return mailed status`() {
        // Given
        val mailedPackage = mailedPackage
        val mailedState = mailedProgressStatus

        // When
        val result = useCase(mailedPackage)

        // Then
        assertEquals(mailedState, result)
    }

    @Test
    fun `invoke with inProgress package Should return inProgress status`() {
        // Given
        val inProgressPackage = inProgressPackage
        val inProgressStatus = inProgressStatus

        // When
        val result = useCase(inProgressPackage)

        // Then
        assertEquals(inProgressStatus, result)
    }

    @Test
    fun `invoke with delivered package Should return delivered status`() {
        // Given
        val deliveredPackage = deliveredPackage
        val deliveredStatus = deliveredProgressStatus

        // When
        val result = useCase(deliveredPackage)

        // Then
        assertEquals(deliveredStatus, result)
    }

}