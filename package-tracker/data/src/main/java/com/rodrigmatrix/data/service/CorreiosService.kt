package com.rodrigmatrix.data.service

import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.model.StatusRequest
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CorreiosService {

    @Headers("Content-Type: application/xml")
    @POST("rest/rastro/rastroMobile")
    suspend fun getStatus(@Body xmlRequestBody: RequestBody): PackageStatusResponse
}