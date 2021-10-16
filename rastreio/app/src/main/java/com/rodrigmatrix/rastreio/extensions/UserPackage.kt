package com.rodrigmatrix.rastreio.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.data.model.PackageLastStatus
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.presentation.theme.*
import com.rodrigmatrix.rastreio.presentation.theme.Teal200

fun UserPackage.getLastStatus(): PackageLastStatus {
    val lastStatus = statusUpdate?.firstOrNull() ?: return PackageLastStatus(
        "Erro ao carregar dados",
        Teal200,
        Icons.Outlined.Done
    )

    val (color, vector) = lastStatus.getStatusIconAndColor()

    return PackageLastStatus(
        lastStatus.description,
        color,
        vector
    )
}

fun StatusUpdate.getStatusIconAndColor(): Pair<Color, ImageVector> {
    return when {
        description.contains("entregue") ->
            Pair(Green, Icons.Outlined.Done)

        description.contains("saiu para entrega") ->
            Pair(Blue, Icons.Outlined.LocalShipping)

        description.contains("em trânsito") ->
            Pair(Teal200, Icons.Outlined.Cached)

        description.contains("Encaminhado") ->
            Pair(Teal200, Icons.Outlined.Cached)

        description.contains("postado") ->
            Pair(Purple700, Icons.Outlined.FlightTakeoff)

        else -> Pair(Orange, Icons.Outlined.Info)
    }
}

