package com.rodrigmatrix.packagetracker.presentation.utils

import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

val PreviewPackageItemsList = listOf(
    UserPackage(
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
        ),
        imagePath = null,
        iconType = "smartphone",
        status = PackageProgressStatus(),
    ),
    UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 6 Pro",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "21/07/2022",
                description = "Entregue",
                from = StatusAddress(),
                hour = "12:00"
            )
        ),
        imagePath = null,
        iconType = "smartphone",
        status = PackageProgressStatus(),
    )
)

val PreviewPackageItem = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "Prime Express",
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
            title = "Objeto em transferência",
            from = StatusAddress(
                city = "CURITIBA",
                state = "PR",
                unitType = "Unidade Operacional",
            ),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Objeto em transferência",
            from = StatusAddress(
                localName = "Alemanha",
                city = "",
                state = "",
                unitType = "Pais",
            ),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            title = "Postado",
            from = StatusAddress(),
            hour = "12:00"
        )
    ),
    imagePath = null,
    iconType = "smartphone",
    status = PackageProgressStatus(),
)

val PreviewPackageProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = true,
    delivered = false,
    outForDelivery = false,
)