package com.rodrigmatrix.domain.usecase

import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class GetPackageNotificationsUseCaseTest {

    private val useCase = GetPackageNotificationsUseCase()

    @Test
    fun case1() {
        val statusUpdateList = listOf(
            StatusUpdate(
                description = "Update",
                from = StatusAddress()
            ),
            StatusUpdate(
                description = "Update 2",
                from = StatusAddress()
            )
        )
        val cachedPackage = UserPackage(
            packageId = "",
            name = "",
            deliveryType = "",
            postalDate = "",
            statusUpdateList = statusUpdateList
        )

        val remotePackage = UserPackage(
            packageId = "",
            name = "",
            deliveryType = "",
            postalDate = "",
            statusUpdateList = statusUpdateList
        )

        assertTrue(useCase(cachedPackage, remotePackage).isEmpty())
    }

    @Test
    fun case2() {
        val cachedStatusUpdateList = listOf(
            StatusUpdate(
                description = "Update",
                from = StatusAddress()
            ),
            StatusUpdate(
                description = "Update 2",
                from = StatusAddress()
            )
        )
        val remoteStatusUpdateList = listOf(
            StatusUpdate(
                description = "Update",
                from = StatusAddress()
            )
        )
        val cachedPackage = UserPackage(
            packageId = "",
            name = "",
            deliveryType = "",
            postalDate = "",
            statusUpdateList = cachedStatusUpdateList
        )

        val remotePackage = UserPackage(
            packageId = "",
            name = "",
            deliveryType = "",
            postalDate = "",
            statusUpdateList = remoteStatusUpdateList
        )

        assertEquals(1, useCase(cachedPackage, remotePackage).size)
    }


}