package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.Flow

interface PackageRepository {

    fun addPackage(name: String,packageId: String): Flow<UserPackage>

    fun getStatus(packageId: String): Flow<UserPackage>

    fun getPackages(): Flow<List<UserPackage>>

    fun fetchPackages(): Flow<Unit>

    fun deletePackage(packageId: String): Flow<Unit>
}