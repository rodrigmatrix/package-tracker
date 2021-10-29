package com.rodrigmatrix.domain.entity

data class UserPackage(
    val packageId: String,
    val name: String,
    val deliveryType: String,
    val postalDate: String,
    val statusUpdateList: List<StatusUpdate>
)