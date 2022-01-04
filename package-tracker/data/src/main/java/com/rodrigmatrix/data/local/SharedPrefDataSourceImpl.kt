package com.rodrigmatrix.data.local

import android.content.SharedPreferences

class SharedPrefDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPrefDataSource {

    override fun getString(key: String, default: String): String {
        return sharedPreferences.getString(key, default).orEmpty()
    }

    override fun setString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}