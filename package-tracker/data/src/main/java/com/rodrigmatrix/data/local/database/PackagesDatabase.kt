package com.rodrigmatrix.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodrigmatrix.data.model.StatusUpdateEntity
import com.rodrigmatrix.data.model.UserPackageEntity

@Database(entities = [UserPackageEntity::class, StatusUpdateEntity::class], version = 2)
abstract class PackagesDatabase : RoomDatabase() {

    abstract fun packagesDAO(): PackagesDAO

    companion object {

        @Volatile private var instance: PackagesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PackagesDatabase::class.java,
            "packages.db"
        ).fallbackToDestructiveMigration().build()
    }

}