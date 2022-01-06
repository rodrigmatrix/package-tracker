package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.mapper.PackageEntityMapper
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PackageRepositoryImpl(
    private val packagesLocalDataSource: PackageLocalDataSource,
    private val packagesRemoteDataSource: PackageRemoteDataSource,
    private val packageMapper: PackageMapper = PackageMapper(),
    private val packageEntityMapper: PackageEntityMapper = PackageEntityMapper()
) : PackageRepository {

    private fun fetchPackage(packageId: String): Flow<UserPackageAndUpdatesEntity> {
        return packagesRemoteDataSource.getPackage(packageId)
            .map { packageEntityMapper.map(it) }
    }

    override fun addPackage(name: String, packageId: String): Flow<UserPackage> {

        return fetchPackage(packageId)
            .map { userPackageEntity ->
                userPackageEntity.name = name
                packagesLocalDataSource.savePackage(userPackageEntity)
                packageMapper.map(userPackageEntity)
            }
    }

    override fun getStatus(packageId: String): Flow<UserPackage> {
        return packagesLocalDataSource.getPackage(packageId)
            .map { packageMapper.map(it) }
    }

    override fun getPackages(): Flow<List<UserPackage>> {
        return packagesLocalDataSource.getAllPackages()
            .map { packagesList ->
                packagesList.map { userPackage ->
                    packageMapper.map(userPackage)
                }
            }
    }

    override fun fetchPackages(): Flow<List<UserPackage>> {
        return flow {
            packagesLocalDataSource.getAllPackages()
                .first()
                .map { userPackage ->
                    fetchPackage(userPackage.id).first().apply {
                        name = userPackage.name
                    }
                }.also { packagesList ->
                    packagesLocalDataSource.savePackages(packagesList)
                    emit(packagesList.map { packageMapper.map(it) })
                }
        }
    }

    override fun deletePackage(packageId: String): Flow<Unit> {
        return packagesLocalDataSource.deletePackage(packageId)
    }

    override fun editPackage(name: String, packageId: String): Flow<Unit> {
        return flow {
            packagesLocalDataSource.getPackage(packageId)
                .first()
                .also {
                    it.name = name
                    packagesLocalDataSource.savePackage(it)
                    emit(Unit)
                }
        }
    }
}