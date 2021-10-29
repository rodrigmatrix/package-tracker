package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.PackageStatusResponse
import kotlinx.coroutines.flow.Flow

interface PackageRemoteDataSource {

    fun getPackage(packageId: String): Flow<PackageStatusResponse>
}