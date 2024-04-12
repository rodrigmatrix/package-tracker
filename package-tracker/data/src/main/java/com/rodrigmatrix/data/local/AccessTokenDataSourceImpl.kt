package com.rodrigmatrix.data.local

import android.content.SharedPreferences

private const val ACCESS_TOKEN_KEY = "access_token_pref"

class AccessTokenDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
) : AccessTokenDataSource {

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    override fun setAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }
}