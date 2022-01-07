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
        )
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
        )
    )
)

val PreviewPackageItem = UserPackage(
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
            description = "Objeto em trânsito",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Objeto em trânsito",
            from = StatusAddress(),
            hour = "12:00"
        ),
        StatusUpdate(
            date = "21/07/2022",
            description = "Postado",
            from = StatusAddress(),
            hour = "12:00"
        )
    )
)

val PreviewPackageProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = true,
    delivered = false
)