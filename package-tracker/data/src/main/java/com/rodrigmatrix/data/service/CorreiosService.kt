package com.rodrigmatrix.data.service

import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.model.StatusRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface CorreiosService {

    @POST("rastreio")
    suspend fun getStatus(@Body statusRequest: StatusRequest): PackageStatusResponse
}