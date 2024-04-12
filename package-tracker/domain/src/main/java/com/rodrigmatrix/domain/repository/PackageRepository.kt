package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.AddPackage
import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.Flow

interface PackageRepository {

    fun addPackage(addPackage: AddPackage): Flow<UserPackage>

    fun getStatus(packageId: String): Flow<UserPackage>

    fun getPackages(): Flow<List<UserPackage>>

    fun fetchPackages(): Flow<List<UserPackage>>

    fun deletePackage(packageId: String): Flow<Unit>

    fun editPackage(addPackage: AddPackage, packageId: String): Flow<Unit>
}