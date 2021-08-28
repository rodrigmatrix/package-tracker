package com.rodrigmatrix.data.local

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.data.local.database.PackagesDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PackageLocalDataSourceImpl(
    private val packagesDAO: PackagesDAO,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PackageLocalDataSource {

    override suspend fun savePackage(userPackage: UserPackageAndUpdates) {
        withContext(coroutineDispatcher) {
            packagesDAO.upsertPackage(
                UserPackage(
                    userPackage.id,
                    userPackage.name,
                    userPackage.deliveryType,
                    userPackage.postalDate
                )
            )
            packagesDAO.deletePackageUpdates(userPackage.id)
            packagesDAO.upsertPackageUpdates(userPackage.statusUpdate.orEmpty())
        }
    }

    override suspend fun getPackage(packageId: String): UserPackageAndUpdates? {
        return withContext(coroutineDispatcher) {
            packagesDAO.getPackage(packageId)
        }
    }

    override suspend fun getAllPackages(): List<UserPackageAndUpdates> {
        return withContext(coroutineDispatcher) {
            packagesDAO.getAllPackages()
        }
    }

}