package com.rodrigmatrix.data.local

import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import kotlinx.coroutines.flow.Flow

interface PackageLocalDataSource {

    fun savePackages(userPackageList: List<UserPackageAndUpdatesEntity>)

    fun savePackage(userPackage: UserPackageAndUpdatesEntity)

    fun getPackage(packageId: String): Flow<UserPackageAndUpdatesEntity>

    fun getAllPackages(): Flow<List<UserPackageAndUpdatesEntity>>

    fun deletePackage(packageId: String): Flow<Unit>
}