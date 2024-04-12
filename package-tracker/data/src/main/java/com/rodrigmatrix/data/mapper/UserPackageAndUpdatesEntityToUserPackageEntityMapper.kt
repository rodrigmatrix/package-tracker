package com.rodrigmatrix.data.mapper

import com.rodrigmatrix.data.model.UserPackageAndUpdatesEntity
import com.rodrigmatrix.data.model.UserPackageEntity

fun UserPackageAndUpdatesEntity.mapToUserPackage() = UserPackageEntity(
    id = id,
    name = name,
    deliveryType = deliveryType,
    postalDate = postalDate,
    iconType = iconType,
    imagePath = imagePath,
)
