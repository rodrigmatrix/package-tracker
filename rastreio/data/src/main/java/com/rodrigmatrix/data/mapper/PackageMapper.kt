package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.core.mapper.Mapper
import com.rodrigmatrix.data.model.*
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

class PackageMapper : Mapper<UserPackageAndUpdatesEntity, UserPackage> {

    override fun map(source: UserPackageAndUpdatesEntity): UserPackage {
        return UserPackage(
            packageId = source.id,
            name = source.name,
            deliveryType = source.deliveryType,
            postalDate = source.postalDate,
            statusUpdateList = source.statusUpdate.orEmpty().map { it.toStatus() }
        )
    }

    private fun StatusUpdateEntity.toStatus(): StatusUpdate {
        return StatusUpdate(
            //FIXME
            statusUpdateType = statusUpdateType,
            description = description,
            date = date,
            from = from.getAddress(),
            to = to?.getAddress()
        )
    }

    private fun StatusAddressEntity.getAddress(): StatusAddress {
        return StatusAddress(
            localName = localName,
            city = city,
            state = state,
            unitType = unitType,
            latitude = latitude,
            longitude = longitude
        )
    }
}