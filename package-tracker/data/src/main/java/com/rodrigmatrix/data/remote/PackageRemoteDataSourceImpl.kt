package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.model.StatusRequest
import com.rodrigmatrix.data.service.CorreiosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PackageRemoteDataSourceImpl(
    private val correiosService: CorreiosService
) : PackageRemoteDataSource {

    override fun getPackage(packageId: String): Flow<PackageStatusResponse> =
        flow { emit(correiosService.getStatus(getRequestBody(packageId))) }

    private fun getRequestBody(packageId: String): RequestBody {
        return getRequestBodyString(packageId).toRequestBody("text/xml".toMediaType())
    }

    private fun getRequestBodyString(packageId: String): String {
        return ""
    }
}