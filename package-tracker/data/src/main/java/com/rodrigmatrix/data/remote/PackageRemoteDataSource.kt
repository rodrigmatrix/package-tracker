package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.response.CorreiosPackageResponse
import kotlinx.coroutines.flow.Flow

interface PackageRemoteDataSource {

    fun getPackage(packageId: String): Flow<CorreiosPackageResponse>
}