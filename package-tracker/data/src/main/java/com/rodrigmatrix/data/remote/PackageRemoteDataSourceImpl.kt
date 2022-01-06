package com.rodrigmatrix.data.remote

import com.rodrigmatrix.core.resource.ResourceProvider
import com.rodrigmatrix.data.R
import com.rodrigmatrix.data.model.CorreiosSecretKeys
import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.service.CorreiosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PackageRemoteDataSourceImpl(
    private val correiosService: CorreiosService,
    private val resourceProvider: ResourceProvider,
    private val correiosSecrets: CorreiosSecretKeys = CorreiosSecretKeys()
) : PackageRemoteDataSource {

    override fun getPackage(packageId: String): Flow<PackageStatusResponse> =
        flow { emit(correiosService.getStatus(getRequestBody(packageId))) }

    private fun getRequestBody(packageId: String): RequestBody {
        return getRequestBodyString(packageId).toRequestBody("text/xml".toMediaType())
    }

    private fun getRequestBodyString(packageId: String): String {
        return resourceProvider.getString(
            R.string.correios_request_body,
            correiosSecrets.userName,
            correiosSecrets.password,
            packageId,
            correiosSecrets.token
        )
    }
}