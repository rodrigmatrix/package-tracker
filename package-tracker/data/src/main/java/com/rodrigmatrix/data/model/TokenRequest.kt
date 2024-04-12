package com.rodrigmatrix.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TokenRequest(
    val requestToken: String,
)