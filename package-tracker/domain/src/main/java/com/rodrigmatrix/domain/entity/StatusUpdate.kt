package com.rodrigmatrix.domain.entity

data class StatusUpdate(
    val statusUpdateType: String = "",
    val description: String = "",
    val date: String = "",
    val from: StatusAddress,
    val to: StatusAddress? = null
)