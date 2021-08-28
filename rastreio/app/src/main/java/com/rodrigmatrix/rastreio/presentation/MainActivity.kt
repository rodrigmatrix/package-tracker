package com.rodrigmatrix.rastreio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import com.rodrigmatrix.rastreio.presentation.details.DetailsScreen
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RastreioTheme {
                DetailsScreen(packageId = "OP212763677BR")
            }
        }
    }

    @Composable
    fun RastreioApp() {

    }

}