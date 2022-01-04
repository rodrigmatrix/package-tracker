package com.rodrigmatrix.data.remote

interface FirebaseRemoteConfigDataSource {

    fun getBoolean(key: String): Boolean

    fun getString(key: String): String
}