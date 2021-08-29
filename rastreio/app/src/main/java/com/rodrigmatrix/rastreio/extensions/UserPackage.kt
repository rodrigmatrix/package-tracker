package com.rodrigmatrix.rastreio.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.data.model.PackageLastStatus
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackageAndUpdates
import com.rodrigmatrix.rastreio.presentation.theme.Purple200
import com.rodrigmatrix.rastreio.presentation.theme.Teal200

fun UserPackageAndUpdates.getLastStatus(): PackageLastStatus {
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

private fun StatusUpdate.getStatusIconAndColor(): Pair<Color, ImageVector> {
    return when {
        description.contains("entregue") ->
            Pair(Teal200, Icons.Outlined.Done)

        description.contains("saiu para entrega") ->
            Pair(Purple200, Icons.Outlined.Done)

        description.contains("em trânsito") ->
            Pair(Purple200, Icons.Outlined.Done)

        description.contains("Encaminhado") ->
            Pair(Purple200, Icons.Outlined.Done)

        description.contains("postado") ->
            Pair(Purple200, Icons.Outlined.Done)

        else -> Pair(Purple200, Icons.Outlined.Done)
    }
}

