package com.rodrigmatrix.data.local

import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import kotlinx.coroutines.flow.Flow

interface PackageLocalDataSource {

    fun savePackage(userPackage: UserPackageAndUpdates)

    fun getPackage(packageId: String): UserPackageAndUpdates?

    fun getAllPackages(): List<UserPackageAndUpdates>

}