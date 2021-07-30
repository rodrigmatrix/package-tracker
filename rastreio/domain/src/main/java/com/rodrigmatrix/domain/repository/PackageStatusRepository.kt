package com.rodrigmatrix.domain.repository

import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.Flow

interface PackageStatusRepository {

    suspend fun getStatus(packageId: String): Flow<UserPackage>

}