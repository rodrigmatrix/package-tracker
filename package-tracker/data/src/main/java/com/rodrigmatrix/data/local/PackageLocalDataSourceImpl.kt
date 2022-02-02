package com.rodrigmatrix.data.local

import com.rodrigmatrix.data.local.database.PackagesDAO
import com.rodrigmatrix.data.local.database.PackagesDatabase
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.model.UserPackageEntity
import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PackageLocalDataSourceImpl(
    private val packagesDAO: PackagesDAO
) : PackageLocalDataSource {

    override fun savePackages(userPackageList: List<UserPackageAndUpdatesEntity>) {
        packagesDAO.savePackages(userPackageList)
    }

    override fun savePackage(userPackage: UserPackageAndUpdatesEntity) {
        packagesDAO.savePackage(userPackage)
    }

    override fun getPackage(packageId: String): Flow<UserPackageAndUpdatesEntity> {
        return packagesDAO.getPackage(packageId)
    }

    override fun getAllPackages(): Flow<List<UserPackageAndUpdatesEntity>> {
        return packagesDAO.getAllPackages()
    }

    override fun deletePackage(packageId: String): Flow<Unit> {
        return flow { emit(packagesDAO.deletePackage(packageId)) }
    }
}