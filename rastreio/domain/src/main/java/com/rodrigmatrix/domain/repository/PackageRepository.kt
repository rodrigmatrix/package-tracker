package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import kotlinx.coroutines.flow.Flow

interface PackageRepository {

    suspend fun addPackage(packageId: String): Flow<UserPackageAndUpdates>

    suspend fun getStatus(packageId: String, forceUpdate: Boolean): Flow<UserPackageAndUpdates>

    suspend fun getAllPackages(forceUpdate: Boolean): Flow<List<UserPackageAndUpdates>>

}