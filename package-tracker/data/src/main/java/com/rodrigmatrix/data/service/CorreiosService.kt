package com.rodrigmatrix.data.service

import com.rodrigmatrix.data.model.PackageStatusResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CorreiosService {

    @Headers("Content-Type: application/xml")
    @POST("rastroMobile")
    suspend fun getStatus(@Body xmlRequestBody: RequestBody): PackageStatusResponse
}