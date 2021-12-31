package com.rodrigmatrix.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "status_update")
data class StatusUpdateEntity(
    val userPackageId: String = "",
    val statusUpdateType: String = "",
    val description: String = "",
    val date: String = "",
    val hour: String = "",
    @Embedded(prefix = "from") val from: StatusAddressEntity,
    @Embedded(prefix = "to") val to: StatusAddressEntity? = null
) {
    @PrimaryKey(autoGenerate = true)
    var statusId: Int = 0
}