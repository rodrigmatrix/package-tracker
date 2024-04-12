package com.rodrigmatrix.domain.entity

data class StatusUpdate(
    val statusUpdateType: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val hour: String = "",
    val from: StatusAddress? = null,
    val to: StatusAddress? = null
) {

    fun getDateWithHour(): String? {
        if (date.isEmpty() && hour.isEmpty()) return null
        return "$date - $hour"
    }
}