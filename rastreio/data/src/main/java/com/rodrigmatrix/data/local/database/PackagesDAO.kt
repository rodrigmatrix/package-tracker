package com.rodrigmatrix.data.local.database

import androidx.room.*
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PackagesDAO {

    @Transaction
    @Query("SELECT * FROM packages")
    abstract fun getAllPackages(): List<UserPackageAndUpdates>

    @Transaction
    @Query("SELECT * FROM packages WHERE id LIKE :packageId")
    abstract fun getPackage(packageId: String): UserPackageAndUpdates?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertPackage(userPackage: UserPackage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertPackageUpdates(statusUpdates: List<StatusUpdate>)

    @Query("DELETE FROM status_update WHERE userPackageId LIKE :packageId")
    abstract fun deletePackageUpdates(packageId: String)

}