package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.model.StatusRequest
import com.rodrigmatrix.data.service.CorreiosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PackageRemoteDataSourceImpl(
    private val correiosService: CorreiosService
) : PackageRemoteDataSource {

    override fun getPackage(packageId: String): Flow<PackageStatusResponse> =
        flow { emit(correiosService.getStatus(StatusRequest(packageId))) }
}