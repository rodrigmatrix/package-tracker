package com.rodrigmatrix.data.service

import com.rodrigmatrix.data.model.CorreiosTokenResponse
import com.rodrigmatrix.data.model.RequestTokenResponse
import com.rodrigmatrix.data.model.TokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface RequestTokenService {

    @GET
    suspend fun getRequestToken(@Url url: String): RequestTokenResponse

    @POST
    suspend fun validateToken(
        @Url url: String,
        @Body tokenRequest: TokenRequest,
        @Header("User-Agent") userAgent: String = "Dart/2.18 (dart:io)",
    ): CorreiosTokenResponse
}