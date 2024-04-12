package com.rodrigmatrix.packagetracker.presentation.addpackage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.InsertEmoticon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseImageDialog(
    onDismiss: () -> Unit,
    onClick: (ChooseImageType) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            ChooseOptionItem(
                title = "Escolher imagem da galeria",
                imageVector = Icons.Outlined.Image,
                type = ChooseImageType.Image,
                onClick = onClick,
            )
            ChooseOptionItem(
                title = "Escolher ícone",
                imageVector = Icons.Outlined.InsertEmoticon,
                type = ChooseImageType.Icon,
                onClick = onClick,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ChooseOptionItem(
    title: String,
    imageVector: ImageVector,
    type: ChooseImageType,
    onClick: (ChooseImageType) -> Unit,
) {
   Row(
       verticalAlignment = Alignment.CenterVertically,
       modifier = Modifier
           .fillMaxWidth()
           .clickable(onClick = { onClick(type) })
           .padding(
               horizontal = 16.dp,
               vertical = 16.dp,
           ),
   ) {
       Icon(
           imageVector = imageVector,
           contentDescription = null,
       )
       Spacer(modifier = Modifier.width(8.dp))
       Text(
           text = title,
           style = MaterialTheme.typography.titleMedium,
       )
   }
}

sealed interface ChooseImageType {
    data object Image : ChooseImageType
    data object Icon : ChooseImageType
}

@PreviewAllTypes
@Composable
private fun ChooseImageDialogPreview() {
    PackageTrackerTheme {
        ChooseImageDialog(
            onDismiss = { },
            onClick = { },
        )
    }
}