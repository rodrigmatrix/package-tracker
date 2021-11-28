package com.rodrigmatrix.packagetracker.presentation.utils

import com.rodrigmatrix.domain.entity.PackageProgressStatus
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage

val packageItemsList = listOf(
    UserPackage(
        "H6XAJ123BN12",
        "Google Pixel 4",
        "",
        "20/07/2022",
        listOf(
            StatusUpdate(
                date = "20/07/2022",
                description = "Saiu para entrega",
                from = StatusAddress()
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
                from = StatusAddress()
            )
        )
    )
)

val packageItem = UserPackage(
    "H6XAJ123BN12",
    "Google Pixel 4",
    "",
    "20/07/2022",
    listOf(
        StatusUpdate(
            date = "20/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress(
                localName = "CTE",
                city = "Fortaleza",

                ),
            to = StatusAddress(
                localName = "CEE",
                city = "Fortaleza",

                )
        ),
        StatusUpdate(
            date = "20/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress(
                localName = "CTE",
                city = "Fortaleza",
                state = "CE"
            ),
            to = StatusAddress(
                localName = "CEE",
                city = "Fortaleza",

                )
        ),
        StatusUpdate(
            date = "20/07/2022",
            description = "Saiu para entrega",
            from = StatusAddress(
                localName = "CTE",
                city = "Fortaleza",

                ),
            to = StatusAddress(
                localName = "CEE",
                city = "Fortaleza",

                )
        )
    )
)

val packageProgressStatus = PackageProgressStatus(
    mailed = true,
    inProgress = true,
    delivered = false
)