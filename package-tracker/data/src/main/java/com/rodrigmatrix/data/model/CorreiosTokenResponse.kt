package com.rodrigmatrix.data.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CorreiosTokenResponse(
    @SerialName("token")
    val token: String? = null
)