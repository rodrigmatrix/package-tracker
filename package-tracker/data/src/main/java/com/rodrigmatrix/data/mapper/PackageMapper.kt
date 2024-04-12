package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.data.model.*
import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.usecase.GetPackageProgressStatusUseCase

class PackageMapper(
    private val getPackageProgressStatusUseCase: GetPackageProgressStatusUseCase,
) : Mapper<UserPackageAndUpdatesEntity, UserPackage> {

    override fun map(source: UserPackageAndUpdatesEntity): UserPackage {
        return UserPackage(
            packageId = source.id,
            name = source.name,
            deliveryType = source.deliveryType.ifEmpty { "CORREIOS" },
            postalDate = source.postalDate,
            statusUpdateList = if (source.statusUpdate.isNullOrEmpty()) {
                listOf(createAddedUpdate())
            } else {
                source.statusUpdate.map { it.toStatus() }
            },
            imagePath = source.imagePath,
            iconType = source.iconType,
            status = PackageProgressStatus(),
        ).run { this.copy(status = getPackageProgressStatusUseCase(this)) }
    }

    private fun StatusUpdateEntity.toStatus(): StatusUpdate {
        return StatusUpdate(
            statusUpdateType = statusUpdateType,
            title = title,
            description = description,
            date = date,
            hour = hour,
            from = from?.getAddress(),
            to = to?.getAddress()
        )
    }

    private fun StatusAddressEntity.getAddress(): StatusAddress {
        return StatusAddress(
            localName = localName,
            city = city,
            state = state,
            unitType = unitType,
        )
    }

    private fun createAddedUpdate(): StatusUpdate = StatusUpdate(
        title = "Encomenda cadastrada",
        description = "Essa encomenda ainda não possui dados na base dos correios. Aguarde por novas atualizações. Se essa for uma encomenda antiga, ela não será atualizada.",
    )
}