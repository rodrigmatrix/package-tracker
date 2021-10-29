package com.rodrigmatrix.data.model

import com.google.gson.annotations.SerializedName

data class StatusRequestEntity(
    @SerializedName("code")
    val packageId: String,
    @SerializedName("type")
    val type: String = "LS"
)