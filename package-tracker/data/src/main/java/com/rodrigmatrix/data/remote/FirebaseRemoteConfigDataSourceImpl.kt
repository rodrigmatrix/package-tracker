package com.rodrigmatrix.data.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class FirebaseRemoteConfigDataSourceImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseRemoteConfigDataSource {

    override fun getBoolean(key: String): Boolean {
        return remoteConfig.getBoolean(key)
    }

    override fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}