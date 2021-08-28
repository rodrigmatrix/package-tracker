package com.rodrigmatrix.data.local

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import kotlinx.coroutines.flow.Flow

interface PackageLocalDataSource {

    suspend fun savePackage(userPackage: UserPackageAndUpdates)

    suspend fun getPackage(packageId: String): UserPackageAndUpdates?

    suspend fun getAllPackages(): List<UserPackageAndUpdates>

}