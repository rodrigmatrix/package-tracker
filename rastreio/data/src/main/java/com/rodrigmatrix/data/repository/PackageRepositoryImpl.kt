package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.mapper.PackageEntityMapper
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.data.model.PackageStatusResponse
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.*

class PackageRepositoryImpl(
    private val packagesLocalDataSource: PackageLocalDataSource,
    private val packagesRemoteDataSource: PackageRemoteDataSource,
    private val packageMapper: PackageMapper = PackageMapper(),
    private val packageEntityMapper: PackageEntityMapper = PackageEntityMapper()
) : PackageRepository {

    override suspend fun addPackage(name: String, packageId: String): Flow<UserPackage> {
        return fetchPackage(packageId)
            .catch { throw it }
            .map { userPackageEntity ->
                userPackageEntity.name = name
                packagesLocalDataSource.savePackage(userPackageEntity)
                packageMapper.map(userPackageEntity)
            }
    }

    override suspend fun getStatus(packageId: String, forceUpdate: Boolean): Flow<UserPackage> {
        return if (forceUpdate) {
            fetchPackage(packageId)
                .map { userPackageEntity ->
                    packagesLocalDataSource.savePackage(userPackageEntity)
                    packageMapper.map(userPackageEntity)
                }
        } else {
            packagesLocalDataSource
                .getPackage(packageId)
                .map { packageMapper.map(it) }
        }
    }

    override suspend fun getAllPackages(forceUpdate: Boolean): Flow<List<UserPackage>> {
        return if (forceUpdate) {
            packagesLocalDataSource
                .getAllPackages()
                .map { packagesList ->
                    packagesList.map { userPackage ->
                        fetchPackage(userPackage.id)
                            .map { userPackageEntity ->
                                packagesLocalDataSource.savePackage(userPackageEntity)
                                packageMapper.map(userPackage)
                            }.first()
                    }
                }
        } else {
            packagesLocalDataSource
                .getAllPackages()
                .map { packagesList ->
                    packagesList.map { userPackage ->
                        packageMapper.map(userPackage)
                    }
                }
        }
    }

    private suspend fun fetchPackage(packageId: String): Flow<UserPackageAndUpdatesEntity> {
        return packagesRemoteDataSource.getPackage(packageId)
            .map { packageEntityMapper.map(it) }
    }
}