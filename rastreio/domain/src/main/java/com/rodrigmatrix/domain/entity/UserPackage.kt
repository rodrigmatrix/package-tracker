package com.rodrigmatrix.domain.entity

import java.util.*

data class UserPackage(
    val id: String,
    val name: String,
    val deliveryType: String,
    val postalDate: Date?,
    val statusUpdate: List<StatusUpdate>
)