package com.rodrigmatrix.domain.entity

import androidx.room.*

@Entity(tableName = "packages")
data class UserPackage(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val deliveryType: String,
    val postalDate: String
)

data class UserPackageAndUpdates(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val deliveryType: String,
    val postalDate: String,
    @Relation(
        parentColumn = "id",
        entityColumn = "userPackageId"
    )
    val statusUpdate: List<StatusUpdate>? = null
)