package com.rodrigmatrix.data.local

import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import kotlinx.coroutines.flow.Flow

interface PackageLocalDataSource {

    suspend fun savePackage(userPackage: UserPackageAndUpdatesEntity)

    suspend fun getPackage(packageId: String): Flow<UserPackageAndUpdatesEntity>

    suspend fun getAllPackages(): Flow<List<UserPackageAndUpdatesEntity>>

}