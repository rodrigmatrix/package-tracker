package com.rodrigmatrix.domain.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "status_update")
data class StatusUpdate(
    val userPackageId: String,
    val statusUpdateType: String,
    val description: String,
    val date: String,
    @Embedded(prefix = "from") val from: StatusAddress,
    @Embedded(prefix = "to") val to: StatusAddress? = null
) {
    @PrimaryKey(autoGenerate = true)
    var statusId: Int = 0
}