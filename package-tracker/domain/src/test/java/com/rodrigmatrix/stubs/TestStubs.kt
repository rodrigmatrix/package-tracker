package com.rodrigmatrix.stubs

import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

internal val packagesList: List<UserPackage>
    get() = listOf(deliveredPackage, inProgressPackage)

internal val deliveredPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "22/07/2022",
            description = "Objeto entregue ao destinatario",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "22/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    )
)

internal val outForDeliveryPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "22/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    )
)

internal val inProgressPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "21/07/2022",
            description = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    )
)

internal val mailedPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "21/07/2022",
            description = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    )
)

internal val emptyPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf()
)

internal val mailedProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = false,
    delivered = false
)

internal val inProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = true,
    delivered = false
)

internal val deliveredProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = true,
    delivered = true
)