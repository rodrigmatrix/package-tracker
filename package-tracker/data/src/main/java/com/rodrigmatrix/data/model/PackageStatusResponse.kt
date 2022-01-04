package com.rodrigmatrix.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class PackageStatusResponse(
    @SerialName("objeto")
    val objeto: List<Objeto>? = null,
    @SerialName("pesquisa")
    val pesquisa: String? = null,
    @SerialName("quantidade")
    val quantidade: String? = null,
    @SerialName("resultado")
    val resultado: String? = null,
    @SerialName("versao")
    val versao: String? = null
)