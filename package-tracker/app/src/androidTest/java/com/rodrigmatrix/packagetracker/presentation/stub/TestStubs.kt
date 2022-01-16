package com.rodrigmatrix.packagetracker.presentation.stub

import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

internal object PackageTestStubs {

    val deliveredPackage = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "22/07/2022",
                description = "Objeto entregue ao destinatario",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "22/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "21/07/2022",
                description = "Em progresso",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "21/07/2022",
                description = "Enviado",
                from = StatusAddress()
            )
        )
    )

    val outForDeliveryPackage = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 4",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "22/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "21/07/2022",
                description = "Em progresso",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "21/07/2022",
                description = "Enviado",
                from = StatusAddress()
            )
        )
    )

    val inProgressPackage = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 6",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "21/07/2022",
                description = "Em progresso",
                from = StatusAddress()
            ),
            StatusUpdate(
                date = "21/07/2022",
                description = "Enviado",
                from = StatusAddress()
            )
        )
    )

    val emptyPackage = UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 6 Pro",
        "",
        "20/07/2022",
        listOf()
    )
}