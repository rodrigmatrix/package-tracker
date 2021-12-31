package com.rodrigmatrix.domain.entity

data class StatusUpdate(
    val statusUpdateType: String = "",
    val description: String = "",
    val date: String = "",
    val hour: String = "",
    val from: StatusAddress,
    val to: StatusAddress? = null
) {

    fun getDateWithHour(): String {
        return "$date - $hour"
    }
}