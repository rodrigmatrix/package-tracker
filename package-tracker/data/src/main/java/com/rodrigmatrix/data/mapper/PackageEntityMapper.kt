package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.data.exceptions.PackageNotFoundException
import com.rodrigmatrix.data.model.*
import com.rodrigmatrix.data.model.response.CorreiosPackageResponse
import com.rodrigmatrix.data.model.response.Evento
import com.rodrigmatrix.data.model.response.Unidade
import com.rodrigmatrix.domain.entity.UserPackage
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class PackageEntityMapper {

    fun map(
        source: CorreiosPackageResponse,
        userPackage: UserPackageEntity?,
    ): UserPackageAndUpdatesEntity {
        val userObject = source.objetos?.firstOrNull() ?: throw PackageNotFoundException("")

        if (userObject.codObjeto.isNullOrEmpty()) {
            throw PackageNotFoundException(userObject.tipoPostal?.categoria.orEmpty())
        }

        return UserPackageAndUpdatesEntity(
            id = userObject.codObjeto,
            name = userPackage?.name ?: userObject.codObjeto,
            deliveryType = userObject.tipoPostal?.categoria.orEmpty(),
            postalDate = userObject.eventos?.firstOrNull()?.dtHrCriado?.getDate().orEmpty(),
            statusUpdate = userObject.eventos?.map { event ->
                event.toStatus(userObject.codObjeto)
            },
            imagePath = userPackage?.imagePath,
            iconType = userPackage?.iconType,
        )
    }

    private fun Evento.toStatus(packageId: String): StatusUpdateEntity {
        return StatusUpdateEntity(
            userPackageId = packageId,
            statusUpdateType = tipo.orEmpty(),
            title = descricao.orEmpty(),
            description = detalhe.orEmpty(),
            date = dtHrCriado?.getDate().orEmpty(),
            hour = dtHrCriado?.getTime().orEmpty(),
            from = unidade?.getAddress(),
            to = unidadeDestino?.getAddress(),
        )
    }

    private fun Unidade.getAddress(): StatusAddressEntity {
        return StatusAddressEntity(
            localName = nome.orEmpty(),
            city = endereco?.cidade.orEmpty(),
            state = endereco?.uf.orEmpty(),
            unitType = tipo,
        )
    }

    private fun String.getDate(): String {
        val localDateTime = DateTime.parse(this).toLocalDateTime()
        return localDateTime.toString("dd/MM/yyyy")
    }

    private fun String.getTime(): String {
        val localDateTime = DateTime.parse(this).toLocalDateTime()
        return localDateTime.toString("HH:mm")
    }
}