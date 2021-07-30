package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.PackageStatusResponse
import kotlinx.coroutines.flow.Flow

interface PackageStatusRemoteDataSource {

    suspend fun getStatus(packageId: String): Flow<PackageStatusResponse>
}