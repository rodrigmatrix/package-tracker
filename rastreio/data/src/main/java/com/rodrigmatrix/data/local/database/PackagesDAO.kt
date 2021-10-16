package com.rodrigmatrix.data.local.database

import androidx.room.*
import com.rodrigmatrix.data.model.StatusUpdateEntity
import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.model.UserPackageEntity
import com.rodrigmatrix.domain.entity.UserPackage
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PackagesDAO {

    @Transaction
    @Query("SELECT * FROM packages")
    abstract fun getAllPackages(): Flow<List<UserPackageAndUpdatesEntity>>

    @Transaction
    @Query("SELECT * FROM packages WHERE id LIKE :packageId")
    abstract fun getPackage(packageId: String): Flow<UserPackageAndUpdatesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertPackage(userPackage: UserPackageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertPackageUpdates(statusUpdates: List<StatusUpdateEntity>)

    @Query("DELETE FROM status_update WHERE userPackageId LIKE :packageId")
    abstract fun deletePackageUpdates(packageId: String)

}