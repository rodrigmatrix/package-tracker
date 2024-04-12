package com.rodrigmatrix.data.local

interface AccessTokenDataSource {

    fun getAccessToken(): String?

    fun setAccessToken(token: String)
}