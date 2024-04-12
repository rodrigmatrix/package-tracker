package com.rodrigmatrix.packagetracker.presentation.addpackage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.ChairAlt
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.LaptopMac
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Piano
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.TableBar
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.ChairAlt
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.Piano
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.TableBar
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import com.rodrigmatrix.packagetracker.presentation.utils.PreviewAllTypes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseIconDialog(
    onDismiss: () -> Unit,
    onIconSelected: (IconOption) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 16.dp,
            ),
        ) {
            items(getIconOptions()) { item ->
                IconItem(
                    item = item,
                    onClick = onIconSelected,
                )
            }
        }
    }
}

@Composable
private fun IconItem(
    item: IconOption,
    onClick: (IconOption) -> Unit,
) {
    IconButton(onClick = { onClick(item) }) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.contentDescription,
            modifier = Modifier.size(32.dp),
        )
    }
}

data class IconOption(
    val icon: ImageVector,
    val type: String,
    val contentDescription: String,
)

fun getIconOptions() = listOf(
    IconOption(
        icon = Icons.Filled.School,
        type = "school",
        contentDescription = "Escola",
    ),
    IconOption(
        icon = Icons.Filled.Science,
        type = "science",
        contentDescription = "Ciência",
    ),
    IconOption(
        icon = Icons.Filled.SportsEsports,
        type = "video_game",
        contentDescription = "Video game",
    ),
    IconOption(
        icon = Icons.Filled.ConfirmationNumber,
        type = "ticket",
        contentDescription = "Ticket",
    ),
    IconOption(
        icon = Icons.Filled.Cake,
        type = "cake",
        contentDescription = "Bolo",
    ),
    IconOption(
        icon = Icons.Filled.SportsSoccer,
        type = "soccer",
        contentDescription = "Esportes",
    ),
    IconOption(
        icon = Icons.Filled.Piano,
        type = "piano",
        contentDescription = "Piano",
    ),
    IconOption(
        icon = Icons.Filled.Smartphone,
        type = "smartphone",
        contentDescription = "Smartphone",
    ),
    IconOption(
        icon = Icons.Filled.Computer,
        type = "computer",
        contentDescription = "Computador",
    ),
    IconOption(
        icon = Icons.Filled.LaptopMac,
        type = "laptop",
        contentDescription = "Notebook",
    ),
    IconOption(
        icon = Icons.Filled.Headphones,
        type = "headphones",
        contentDescription = "Headphones",
    ),
    IconOption(
        icon = Icons.Filled.Tv,
        type = "tv",
        contentDescription = "Tv",
    ),
    IconOption(
        icon = Icons.Filled.Watch,
        type = "watch",
        contentDescription = "Relógio",
    ),
    IconOption(
        icon = Icons.Filled.Bed,
        type = "bed",
        contentDescription = "Cama",
    ),
    IconOption(
        icon = Icons.Filled.ChairAlt,
        type = "chair",
        contentDescription = "Cadeira",
    ),
    IconOption(
        icon = Icons.Filled.TableBar,
        type = "table",
        contentDescription = "Mesa",
    ),
    IconOption(
        icon = Icons.Filled.Face,
        type = "face",
        contentDescription = "Rosto",
    ),
    IconOption(
        icon = Icons.Filled.ShoppingBag,
        type = "shopping_bag",
        contentDescription = "Cesta de compras",
    ),
    IconOption(
        icon = Icons.Filled.ShoppingCart,
        type = "shopping_cart",
        contentDescription = "Carrinho de compras",
    ),
    IconOption(
        icon = Icons.Filled.CreditCard,
        type = "credit_card",
        contentDescription = "Cartão de crédito",
    ),
    IconOption(
        icon = Icons.Filled.Lightbulb,
        type = "lightbulb",
        contentDescription = "Lámpada",
    ),
    IconOption(
        icon = Icons.Filled.Print,
        type = "print",
        contentDescription = "Impressora",
    ),
    IconOption(
        icon = Icons.Filled.Wallet,
        type = "wallet",
        contentDescription = "Carteira",
    ),
    IconOption(
        icon = Icons.Filled.Weekend,
        type = "couch",
        contentDescription = "Sofá",
    ),
    IconOption(
        icon = Icons.Filled.PhotoCamera,
        type = "camera",
        contentDescription = "Câmera",
    ),
    IconOption(
        icon = Icons.Filled.Style,
        type = "style",
        contentDescription = "Capinha de celular",
    ),
    IconOption(
        icon = Icons.AutoMirrored.Filled.MenuBook,
        type = "book",
        contentDescription = "Livro",
    ),
    IconOption(
        icon = Icons.Filled.Keyboard,
        type = "keyboard",
        contentDescription = "Teclado",
    ),
    IconOption(
        icon = Icons.Filled.LocalShipping,
        type = "local_shipping",
        contentDescription = "Caminhão",
    ),
    IconOption(
        icon = Icons.Filled.Album,
        type = "album",
        contentDescription = "Album",
    ),
)

@PreviewAllTypes
@Composable
private fun ChooseIconDialogPreview() {
    PackageTrackerTheme {
        ChooseIconDialog(
            onDismiss = { },
            onIconSelected = { },
        )
    }
}