package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.data.model.Destino
import com.rodrigmatrix.data.model.Evento
import com.rodrigmatrix.data.model.PackageStatusResponse

class PackageMapper: Mapper<PackageStatusResponse, UserPackageAndUpdates> {

    override fun map(source: PackageStatusResponse): UserPackageAndUpdates {
        val userPackage = source.objeto?.firstOrNull() ?: throw Exception()

        return UserPackageAndUpdates(
            id = userPackage.numero,
            name = userPackage.nome,
            deliveryType = userPackage.categoria,
            postalDate = userPackage.evento.first().dataPostagem,
            statusUpdate = userPackage.evento.map { event -> event.toStatus(userPackage.numero) }
        )
    }

    private fun Evento.toStatus(packageId: String): StatusUpdate {
        return StatusUpdate(
            userPackageId = packageId,
            //FIXME
            statusUpdateType = tipo,
            description = descricao,
            date = data,
            from = getAddress(),
            to = destino?.firstOrNull()?.getDestinationAddress()
        )
    }

    private fun Evento.getAddress(): StatusAddress {
        return StatusAddress(
            localName = unidade.local,
            city = unidade.cidade,
            state = unidade.uf,
            unitType = unidade.tipounidade,
            latitude = unidade.endereco.latitude.toLongOrNull() ?: 0L,
            longitude = unidade.endereco.longitude.toLongOrNull() ?: 0L
        )
    }

    private fun Destino.getDestinationAddress(): StatusAddress {
        return StatusAddress(
            localName = local,
            city = cidade,
            state = uf,
            unitType = null,
            latitude = endereco.latitude.toLongOrNull() ?: 0L,
            longitude = endereco.longitude.toLongOrNull() ?: 0L
        )
    }

}