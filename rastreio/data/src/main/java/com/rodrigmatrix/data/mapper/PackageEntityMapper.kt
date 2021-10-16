package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.data.model.*

class PackageEntityMapper: Mapper<PackageStatusResponse, UserPackageAndUpdatesEntity> {

    override fun map(source: PackageStatusResponse): UserPackageAndUpdatesEntity {
        val userPackage = source.objeto?.firstOrNull() ?: throw Exception()

        return UserPackageAndUpdatesEntity(
            id = userPackage.numero,
            name = userPackage.nome,
            deliveryType = userPackage.categoria,
            postalDate = userPackage.evento.first().dataPostagem,
            statusUpdate = userPackage.evento.map { event -> event.toStatus(userPackage.numero) }
        )
    }

    private fun Evento.toStatus(packageId: String): StatusUpdateEntity {
        return StatusUpdateEntity(
            userPackageId = packageId,
            //FIXME
            statusUpdateType = tipo,
            description = descricao,
            date = data,
            from = getAddress(),
            to = destino?.firstOrNull()?.getDestinationAddress()
        )
    }

    private fun Evento.getAddress(): StatusAddressEntity {
        return StatusAddressEntity(
            localName = unidade.local,
            city = unidade.cidade,
            state = unidade.uf,
            unitType = unidade.tipounidade,
            latitude = unidade.endereco.latitude.toLongOrNull() ?: 0L,
            longitude = unidade.endereco.longitude.toLongOrNull() ?: 0L
        )
    }

    private fun Destino.getDestinationAddress(): StatusAddressEntity {
        return StatusAddressEntity(
            localName = local,
            city = cidade,
            state = uf,
            unitType = null,
            latitude = endereco.latitude.toLongOrNull() ?: 0L,
            longitude = endereco.longitude.toLongOrNull() ?: 0L
        )
    }

}