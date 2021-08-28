package com.rodrigmatrix.rastreio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rodrigmatrix.rastreio.presentation.details.Details
import com.rodrigmatrix.rastreio.ui.theme.RastreioTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RastreioTheme {
                Details(packageId = "OP212763677BR")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RastreioTheme {
        Details(packageId = "")
    }
}