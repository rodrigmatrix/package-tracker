package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.Flow

interface PackageRepository {

    suspend fun addPackage(name: String,packageId: String): Flow<UserPackage>

    suspend fun getStatus(packageId: String): Flow<UserPackage>

    suspend fun getPackages(): Flow<List<UserPackage>>

    suspend fun fetchPackages(): Flow<Unit>
}