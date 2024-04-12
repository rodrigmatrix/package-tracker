package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.mapper.PackageEntityMapper
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.data.mapper.mapToUserPackage
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.model.UserPackageEntity
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.domain.entity.AddPackage
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageRepository
import kotlinx.coroutines.flow.*

class PackageRepositoryImpl(
    private val packagesLocalDataSource: PackageLocalDataSource,
    private val packagesRemoteDataSource: PackageRemoteDataSource,
    private val packageMapper: PackageMapper,
    private val packageEntityMapper: PackageEntityMapper = PackageEntityMapper(),
) : PackageRepository {

    private fun fetchPackage(
        packageId: String,
        userPackage: UserPackageEntity?,
    ): Flow<UserPackageAndUpdatesEntity> {
        return packagesRemoteDataSource.getPackage(packageId)
            .map { packageEntityMapper.map(source = it, userPackage = userPackage) }
    }

    override fun addPackage(addPackage: AddPackage): Flow<UserPackage> {
        return fetchPackage(packageId = addPackage.packageId, userPackage = null)
            .map { userPackageEntity ->
                val updatedUserPackageEntity = userPackageEntity.copy(
                    name = addPackage.name,
                    imagePath = addPackage.imagePath,
                    iconType = addPackage.iconType,
                )
                packagesLocalDataSource.savePackage(updatedUserPackageEntity)
                packageMapper.map(updatedUserPackageEntity)
            }
    }

    override fun getStatus(packageId: String): Flow<UserPackage> {
        return packagesLocalDataSource.getPackage(packageId)
            .map { packageMapper.map(it) }
    }

    override fun getPackages(): Flow<List<UserPackage>> {
        return packagesLocalDataSource.getAllPackages()
            .map { packagesList ->
                packagesList.map { userPackageEntity ->
                    packageMapper.map(userPackageEntity)
                }
            }
    }

    override fun fetchPackages(): Flow<List<UserPackage>> {
        return flow {
            packagesLocalDataSource.getAllPackages()
                .first()
                .map { userPackageAndUpdates ->
                    fetchPackage(userPackageAndUpdates.id, userPackageAndUpdates.mapToUserPackage())
                        .catch { emit(userPackageAndUpdates) }
                        .first()
                }.also { packagesList ->
                    packagesLocalDataSource.savePackages(packagesList)
                    emit(packagesList.map { packageMapper.map(it) })
                }
        }
    }

    override fun deletePackage(packageId: String): Flow<Unit> {
        return packagesLocalDataSource.deletePackage(packageId)
    }

    override fun editPackage(
        addPackage: AddPackage,
        packageId: String
    ): Flow<Unit> {
        return flow {
            packagesLocalDataSource.getPackage(packageId)
                .first()
                .also {
                    val editedPackaged = it.copy(
                        name = addPackage.name,
                        imagePath = addPackage.imagePath,
                        iconType = addPackage.iconType,
                    )
                    packagesLocalDataSource.savePackage(editedPackaged)
                    emit(Unit)
                }
        }
    }
}