package com.rodrigmatrix.data.service

import com.rodrigmatrix.data.model.response.CorreiosPackageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CorreiosService {

    @GET("v1/sro-rastro/{packageId}")
    suspend fun getPackage(
        @Path("packageId") packageId: String,
        @Header("User-Agent") userAgent: String = "Dart/2.18 (dart:io)",
    ): CorreiosPackageResponse
}