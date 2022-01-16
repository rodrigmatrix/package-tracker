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

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    override fun setBoolean(key: String, value: Boolean) {
        return sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getInt(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

    override fun setInt(key: String, value: Int) {
        return sharedPreferences.edit().putInt(key, value).apply()
    }
}