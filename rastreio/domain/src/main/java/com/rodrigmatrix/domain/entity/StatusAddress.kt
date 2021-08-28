package com.rodrigmatrix.domain.entity

class StatusAddress(
    val localName: String = "",
    val city: String = "",
    val state: String = "",
    val unitType: String? = null,
    val latitude: Long = 0L,
    val longitude: Long = 0L
)