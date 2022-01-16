package com.rodrigmatrix.domain.entity

data class PackageNotificationsPreference(
    val enabled: Boolean,
    val minutesUpdateInterval: Int
)