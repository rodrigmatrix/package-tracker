package com.rodrigmatrix.data.model

import com.rodrigmatrix.data.BuildConfig

data class CorreiosSecretKeys(
    val userName: String = BuildConfig.CORREIOS_USER,
    val password: String = BuildConfig.CORREIOS_PASSWORD,
    val token: String = BuildConfig.CORREIOS_TOKEN,
)