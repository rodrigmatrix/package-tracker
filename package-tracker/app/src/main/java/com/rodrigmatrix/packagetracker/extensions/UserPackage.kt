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
        "Encomenda não encontrada na base de dados",
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

fun StatusUpdate?.getStatusIconAndColor(): Pair<Color, ImageVector> {
    val statusDescription = this?.description.orEmpty().lowercase()
    return when {
        this == null ->
            Pair(md_theme_light_error, Icons.Outlined.Info)

        statusDescription.contains("entregue") ->
            Pair(theme_light_done, Icons.Outlined.Done)

        statusDescription.contains("saiu para entrega") ->
            Pair(theme_light_inRoute, Icons.Outlined.LocalShipping)

        statusDescription.contains("em trânsito") ->
            Pair(theme_light_inProgress, Icons.Outlined.Cached)

        statusDescription.contains("encaminhado") ->
            Pair(theme_light_inProgress, Icons.Outlined.Cached)

        statusDescription.contains("postado") ->
            Pair(theme_light_shipped, Icons.Outlined.FlightTakeoff)

        else -> Pair(theme_light_alert, Icons.Outlined.Info)
    }
}

