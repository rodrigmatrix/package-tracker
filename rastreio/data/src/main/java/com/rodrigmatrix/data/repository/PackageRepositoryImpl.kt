package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.local.PackageLocalDataSource
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.data.remote.PackageRemoteDataSource
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.domain.repository.PackageRepository
import com.rodrigmatrix.domain.usecase.AddPackageUseCase
import com.rodrigmatrix.domain.usecase.GetAllPackagesUseCase
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import kotlinx.coroutines.flow.*

class PackageRepositoryImpl(
    private val packagesLocalDataSource: PackageLocalDataSource,
    private val packagesRemoteDataSource: PackageRemoteDataSource,
    private val packageMapper: PackageMapper = PackageMapper()
) : PackageRepository {

    override suspend fun addPackage(packageId: String): Flow<UserPackageAndUpdates> {
        return packagesRemoteDataSource.getPackage(packageId)
            .map { packageResponse ->
                packageMapper.map(packageResponse).also { userPackage ->
                    packagesLocalDataSource.savePackage(userPackage)
                }
            }
    }

    override suspend fun getStatus(packageId: String, forceUpdate: Boolean): Flow<UserPackageAndUpdates> {
        val localPackage = packagesLocalDataSource.getPackage(packageId)

        return when {
            forceUpdate -> addPackage(packageId)

            localPackage != null -> flow { emit(localPackage) }

            else -> addPackage(packageId)
        }
    }

    override suspend fun getAllPackages(): Flow<List<UserPackageAndUpdates>> {
        return flow { emit(packagesLocalDataSource.getAllPackages()) }
    }

}