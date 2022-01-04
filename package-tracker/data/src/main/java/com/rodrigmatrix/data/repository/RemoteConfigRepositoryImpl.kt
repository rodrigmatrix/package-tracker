package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.remote.FirebaseRemoteConfigDataSource
import com.rodrigmatrix.domain.repository.RemoteConfigRepository

class RemoteConfigRepositoryImpl(
    private val firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
): RemoteConfigRepository {

    override fun getBoolean(key: String): Boolean {
        return firebaseRemoteConfigDataSource.getBoolean(key)
    }

    override fun getString(key: String): String {
        return firebaseRemoteConfigDataSource.getString(key)
    }
}