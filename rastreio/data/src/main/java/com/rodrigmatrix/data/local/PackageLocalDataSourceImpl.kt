package com.rodrigmatrix.data.local

import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.data.local.database.PackagesDAO
import kotlinx.coroutines.flow.Flow

class PackageLocalDataSourceImpl(
    private val packagesDAO: PackagesDAO
) : PackageLocalDataSource {

    override fun savePackage(userPackage: UserPackageAndUpdates) {
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

    override fun getPackage(packageId: String): UserPackageAndUpdates? {
        return packagesDAO.getPackage(packageId)
    }

    override fun getAllPackages(): List<UserPackageAndUpdates> {
        return packagesDAO.getAllPackages()
    }

}