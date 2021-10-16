package com.rodrigmatrix.data.local

import com.rodrigmatrix.data.local.database.PackagesDAO
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.model.UserPackageEntity
import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PackageLocalDataSourceImpl(
    private val packagesDAO: PackagesDAO,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PackageLocalDataSource {

    override suspend fun savePackage(userPackage: UserPackageAndUpdatesEntity) {
        withContext(coroutineDispatcher) {
            packagesDAO.upsertPackage(
                UserPackageEntity(
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

    override suspend fun getPackage(packageId: String): Flow<UserPackageAndUpdatesEntity> {
        return withContext(coroutineDispatcher) {
            packagesDAO.getPackage(packageId)
        }
    }

    override suspend fun getAllPackages(): Flow<List<UserPackageAndUpdatesEntity>> {
        return withContext(coroutineDispatcher) {
            packagesDAO.getAllPackages()
        }
    }

}