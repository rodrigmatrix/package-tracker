package com.rodrigmatrix.data.local

interface SharedPrefDataSource {

    fun getString(key: String, default: String): String

    fun setString(key: String, value: String?)
}