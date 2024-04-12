package com.rodrigmatrix.data.model.response

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CorreiosPackageResponse(
    val objetos: List<Objeto>? = null,
    val quantidade: Int? = null,
    val resultado: String? = null,
    val versao: String? = null
)

@Keep
@Serializable
data class Endereco(
    val cidade: String? = null,
    val uf: String? = null
)

@Keep
@Serializable
data class Evento(
    val codigo: String? = null,
    val descricao: String? = null,
    val detalhe: String? = null,
    val dtHrCriado: String? = null,
    val tipo: String? = null,
    val unidade: Unidade? = null,
    val unidadeDestino: Unidade? = null,
    val urlIcone: String? = null
)

@Keep
@Serializable
data class Objeto(
    val bloqueioObjeto: Boolean? = null,
    val mensagem: String? = null,
    val codObjeto: String? = null,
    val eventos: List<Evento>? = null,
    val habilitaAutoDeclaracao: Boolean? = null,
    val habilitaCrowdshipping: Boolean? = null,
    val habilitaLocker: Boolean? = null,
    val habilitaPercorridaCarteiro: Boolean? = null,
    val permiteEncargoImportacao: Boolean? = null,
    val possuiLocker: Boolean? = null,
    val temServicoAr: Boolean? = null,
    val tipoPostal: TipoPostal? = null
)

@Keep
@Serializable
data class TipoPostal(
    val categoria: String? = null,
    val descricao: String? = null,
    val sigla: String? = null
)

@Keep
@Serializable
data class Unidade(
    val endereco: Endereco?,
    val nome: String? = null,
    val tipo: String? = null,
)