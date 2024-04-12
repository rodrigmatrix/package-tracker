package com.rodrigmatrix.domain.repository

interface AccessTokenRepository {

    fun getAccessToken(): String

    suspend fun updateAccessToken(): String
}