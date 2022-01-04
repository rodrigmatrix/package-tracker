package com.rodrigmatrix.domain.repository

interface RemoteConfigRepository {

    fun getBoolean(key: String): Boolean

    fun getString(key: String): String
}