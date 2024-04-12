package com.rodrigmatrix.data.model

import androidx.room.*

@Entity(tableName = "packages")
data class UserPackageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val deliveryType: String,
    val postalDate: String,
    val iconType: String?,
    val imagePath: String?,
)

data class UserPackageAndUpdatesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var name: String,
    val deliveryType: String,
    val postalDate: String,
    @Relation(
        parentColumn = "id",
        entityColumn = "userPackageId"
    )
    val statusUpdate: List<StatusUpdateEntity>? = null,
    val iconType: String?,
    val imagePath: String?,
)