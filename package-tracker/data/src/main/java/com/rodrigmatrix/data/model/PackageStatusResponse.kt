package com.rodrigmatrix.data.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

@Keep
@Serializable
data class Destino(
    @SerialName("bairro")
    val bairro: String? = null,
    @SerialName("cidade")
    val cidade: String? = null,
    @SerialName("codigo")
    val codigo: String? = null,
    @SerialName("endereco")
    val endereco: Endereco? = null,
    @SerialName("local")
    val local: String? = null,
    @SerialName("uf")
    val uf: String? = null
)

@Keep
@Serializable
data class Endereco(
    @SerialName("bairro")
    val bairro: String? = null,
    @SerialName("cep")
    val cep: String? = null,
    @SerialName("codigo")
    val codigo: String? = null,
    @SerialName("complemento")
    val complemento: String? = null,
    @SerialName("latitude")
    val latitude: String? = null,
    @SerialName("localidade")
    val localidade: String? = null,
    @SerialName("logradouro")
    val logradouro: String? = null,
    @SerialName("longitude")
    val longitude: String? = null,
    @SerialName("numero")
    val numero: String? = null,
    @SerialName("uf")
    val uf: String? = null
)


@Keep
@Serializable
data class Evento(
    @SerialName("cepDestino")
    val cepDestino: String? = null,
    @SerialName("criacao")
    val criacao: String? = null,
    @SerialName("data")
    val `data`: String? = null,
    @SerialName("dataPostagem")
    val dataPostagem: String? = null,
    @SerialName("descricao")
    val descricao: String? = null,
    @SerialName("destino")
    val destino: List<Destino>? = null,
    @SerialName("detalhe")
    val detalhe: String? = null,
    @SerialName("diasUteis")
    val diasUteis: String? = null,
    @SerialName("hora")
    val hora: String? = null,
    @SerialName("postagem")
    val postagem: Postagem? = null,
    @SerialName("prazoGuarda")
    val prazoGuarda: String? = null,
    @SerialName("recebedor")
    val recebedor: Recebedor? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("tipo")
    val tipo: String? = null,
    @SerialName("unidade")
    val unidade: Unidade? = null
)

@Keep
@Serializable
data class Objeto(
    @SerialName("categoria")
    val categoria: String? = null,
    @SerialName("evento")
    val evento: List<Evento>? = null,
    @SerialName("nome")
    val nome: String? = null,
    @SerialName("numero")
    val numero: String? = null,
    @SerialName("sigla")
    val sigla: String? = null
)

@Keep
@Serializable
data class Postagem(
    @SerialName("ar")
    val ar: String? = null,
    @SerialName("cepdestino")
    val cepdestino: String? = null,
    @SerialName("datapostagem")
    val datapostagem: String? = null,
    @SerialName("dataprogramada")
    val dataprogramada: String? = null,
    @SerialName("dh")
    val dh: String? = null,
    @SerialName("mp")
    val mp: String? = null,
    @SerialName("peso")
    val peso: String? = null,
    @SerialName("prazotratamento")
    val prazotratamento: String? = null,
    @SerialName("volume")
    val volume: String? = null
)


@Keep
@Serializable
data class Recebedor(
    @SerialName("comentario")
    val comentario: String? = null,
    @SerialName("documento")
    val documento: String? = null,
    @SerialName("nome")
    val nome: String? = null
)


@Keep
@Serializable
data class Unidade(
    @SerialName("cidade")
    val cidade: String? = null,
    @SerialName("codigo")
    val codigo: String? = null,
    @SerialName("endereco")
    val endereco: Endereco? = null,
    @SerialName("local")
    val local: String? = null,
    @SerialName("sto")
    val sto: String? = null,
    @SerialName("tipounidade")
    val tipounidade: String? = null,
    @SerialName("uf")
    val uf: String? = null
)