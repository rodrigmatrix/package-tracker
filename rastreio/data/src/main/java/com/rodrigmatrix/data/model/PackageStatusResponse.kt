package com.rodrigmatrix.data.model

data class PackageStatusResponse(
    val objeto: List<Objeto>?,
    val pesquisa: String?,
    val quantidade: String?,
    val resultado: String?,
    val versao: String?
)

data class Objeto(
    val categoria: String?,
    val evento: List<Evento>?,
    val nome: String?,
    val numero: String?,
    val sigla: String?
)

class Recebedor(
)

data class Unidade(
    val cidade: String?,
    val codigo: String?,
    val endereco: EnderecoX?,
    val local: String?,
    val sto: String?,
    val tipounidade: String?,
    val uf: String?
)

data class Evento(
    val cepDestino: String?,
    val criacao: String?,
    val `data`: String?,
    val dataPostagem: String?,
    val descricao: String?,
    val destino: List<Destino>?,
    val detalhe: String?,
    val detalheOEC: DetalheOEC?,
    val diasUteis: String?,
    val hora: String?,
    val prazoGuarda: String?,
    val recebedor: Recebedor?,
    val status: String?,
    val tipo: String?,
    val unidade: Unidade?
)

data class EnderecoX(
    val bairro: String?,
    val cep: String?,
    val codigo: String?,
    val complemento: String?,
    val latitude: String?,
    val localidade: String?,
    val logradouro: String?,
    val longitude: String?,
    val numero: String?,
    val uf: String?
)

data class Endereco(
    val bairro: String?,
    val cep: String?,
    val codigo: String?,
    val latitude: String?,
    val localidade: String?,
    val logradouro: String?,
    val longitude: String?,
    val numero: String?,
    val uf: String?
)

data class Destino(
    val bairro: String?,
    val cidade: String?,
    val codigo: String?,
    val endereco: Endereco?,
    val local: String?,
    val uf: String?
)

data class DetalheOEC(
    val carteiro: String?,
    val distrito: String?,
    val lista: String?,
    val unidade: String?
)