package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.service.CorreiosService
import com.rodrigmatrix.domain.entity.StatusRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PackageStatusRemoteDataSourceImpl(
    private val correiosService: CorreiosService
) : PackageStatusRemoteDataSource {


    override suspend fun getStatus(packageId: String): Flow<PackageStatusResponse> =
        flow { emit(correiosService.getStatus(StatusRequest(packageId))) }

}