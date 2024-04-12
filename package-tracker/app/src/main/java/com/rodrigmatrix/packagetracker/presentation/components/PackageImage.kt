package com.rodrigmatrix.packagetracker.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rodrigmatrix.packagetracker.presentation.addpackage.IconOption

@Composable
fun PackageImage(
    imagePath: String?,
    icon: IconOption?,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    iconTint: Color = MaterialTheme.colorScheme.primary,
) {
    if (imagePath != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .build(),
            contentDescription = "Imagem da encomenda",
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .size(
                    height = 80.dp,
                    width = 80.dp,
                ),
        )
    } else if (icon != null) {
        Icon(
            imageVector = icon.icon,
            contentDescription = icon.contentDescription,
            tint = iconTint,
            modifier = modifier
                .clip(shape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .size(80.dp)
                .padding(8.dp),
        )
    }
}