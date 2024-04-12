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
            title = "Objeto entregue ao destinatario",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "22/07/2022",
            title = "Saiu para entrega",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    ),
    iconType = "",
    imagePath = "",
    status = PackageProgressStatus(delivered = true),
)

internal val outForDeliveryPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "22/07/2022",
            title = "Saiu para entrega",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    ),
    iconType = "",
    imagePath = "",
    status = PackageProgressStatus(outForDelivery = true),
)

internal val inProgressPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "21/07/2022",
            title = "Em progresso",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Enviado",
            from = StatusAddress(),
            hour = "12:00"
        )
    ),
    iconType = "",
    imagePath = "",
    status = PackageProgressStatus(inProgress = true),
)

internal val mailedPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "SEDEX",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "21/07/2022",
            title = "Postado",
            from = StatusAddress(),
            hour = "12:00"
        )
    ),
    iconType = "",
    imagePath = "",
    status = PackageProgressStatus(mailed = true),
)

internal val emptyPackage = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "CORREIOS",
    "20/07/2022",
    listOf(
        StatusUpdate(
            title = "Encomenda cadastrada",
            description = "Essa encomenda ainda não possui dados na base dos correios. Aguarde " +
                "por novas atualizações. Se essa for uma encomenda antiga, ela não será atualizada.",
        )
    ),
    iconType = "",
    imagePath = "",
    status = PackageProgressStatus(),
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