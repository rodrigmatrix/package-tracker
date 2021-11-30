package com.rodrigmatrix.packagetracker.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.data.model.PackageLastStatus
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.theme.*

fun UserPackage.getLastStatus(): PackageLastStatus {
    val lastStatus = statusUpdateList.firstOrNull() ?: return PackageLastStatus(
        "Erro ao carregar dados",
        md_theme_light_primary,
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
            Pair(theme_light_done, Icons.Outlined.Done)

        description.contains("saiu para entrega") ->
            Pair(theme_light_inRoute, Icons.Outlined.LocalShipping)

        description.contains("em trânsito") ->
            Pair(theme_light_inProgress, Icons.Outlined.Cached)

        description.contains("Encaminhado") ->
            Pair(theme_light_inProgress, Icons.Outlined.Cached)

        description.contains("postado") ->
            Pair(theme_light_shipped, Icons.Outlined.FlightTakeoff)

        else -> Pair(theme_light_alert, Icons.Outlined.Info)
    }
}

