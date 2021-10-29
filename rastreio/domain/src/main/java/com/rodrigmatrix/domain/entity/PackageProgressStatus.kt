package com.rodrigmatrix.domain.entity

data class PackageProgressStatus(
    val mailed: Boolean,
    val inProgress: Boolean,
    val delivered: Boolean
)