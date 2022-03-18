package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.data.exceptions.PackageNotFoundException
import com.rodrigmatrix.data.model.*

class PackageEntityMapper: Mapper<PackageStatusResponse, UserPackageAndUpdatesEntity> {

    override fun map(source: PackageStatusResponse): UserPackageAndUpdatesEntity {
        val userPackage = source.objeto?.firstOrNull() ?: throw PackageNotFoundException("")

        if (userPackage.sigla.isNullOrEmpty()) {
            throw PackageNotFoundException(userPackage.categoria.orEmpty())
        }

        return UserPackageAndUpdatesEntity(
            id = userPackage.numero.orEmpty(),
            name = userPackage.nome.orEmpty(),
            deliveryType = userPackage.categoria.orEmpty(),
            postalDate = userPackage.evento?.firstOrNull()?.dataPostagem.orEmpty(),
            statusUpdate = userPackage.evento?.map { event ->
                event.toStatus(userPackage.numero.orEmpty())
            }
        )
    }

    private fun Evento.toStatus(packageId: String): StatusUpdateEntity {
        return StatusUpdateEntity(
            userPackageId = packageId,
            statusUpdateType = tipo.orEmpty(),
            description = descricao.orEmpty(),
            date = data.orEmpty(),
            hour = hora.orEmpty(),
            from = getAddress(),
            to = destino?.firstOrNull()?.getDestinationAddress()
        )
    }

    private fun Evento.getAddress(): StatusAddressEntity {
        return StatusAddressEntity(
            localName = unidade?.local.orEmpty(),
            city = unidade?.cidade.orEmpty(),
            state = unidade?.uf.orEmpty(),
            unitType = unidade?.tipounidade.orEmpty(),
            latitude = unidade?.endereco?.latitude?.toLongOrNull() ?: 0L,
            longitude = unidade?.endereco?.longitude?.toLongOrNull() ?: 0L
        )
    }

    private fun Destino.getDestinationAddress(): StatusAddressEntity {
        return StatusAddressEntity(
            localName = local.orEmpty(),
            city = cidade.orEmpty(),
            state = uf.orEmpty(),
            unitType = null,
            latitude = endereco?.latitude?.toLongOrNull() ?: 0L,
            longitude = endereco?.longitude?.toLongOrNull() ?: 0L
        )
    }

}