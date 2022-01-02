package com.rodrigmatrix.packagetracker.presentation.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "Desenvolvido por Rodrigo Gomes")
        Text(text = "Esse é um projeto open source e seu código pode ser encontrado no meu github")

    }
}