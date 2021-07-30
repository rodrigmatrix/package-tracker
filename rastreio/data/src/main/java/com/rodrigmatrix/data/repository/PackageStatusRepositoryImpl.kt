package com.rodrigmatrix.data.repository

import com.rodrigmatrix.data.remote.PackageStatusRemoteDataSource
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.data.mapper.PackageMapper
import com.rodrigmatrix.domain.repository.PackageStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PackageStatusRepositoryImpl(
    private val packageStatusRemoteDataSource: PackageStatusRemoteDataSource,
    private val packageMapper: PackageMapper
) : PackageStatusRepository {

    override suspend fun getStatus(packageId: String): Flow<UserPackage> {
        return packageStatusRemoteDataSource.getStatus(packageId)
            .map { packageMapper.map(it) ?: throw Throwable() }
    }

}