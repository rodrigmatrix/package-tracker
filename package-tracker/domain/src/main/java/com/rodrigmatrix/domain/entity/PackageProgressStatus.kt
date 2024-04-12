package com.rodrigmatrix.domain.entity

data class PackageProgressStatus(
    val mailed: Boolean = false,
    val inProgress: Boolean = false,
    val delivered: Boolean = false,
    val outForDelivery: Boolean = false,
)