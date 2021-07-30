package com.rodrigmatrix.domain.entity

class StatusAddress(
    val localName: String,
    val city: String,
    val state: String,
    val unitType: String?,
    val latitude: Long,
    val longitude: Long
)