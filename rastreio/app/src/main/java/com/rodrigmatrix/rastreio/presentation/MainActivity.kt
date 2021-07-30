package com.rodrigmatrix.rastreio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.repository.PackageStatusRepository
import com.rodrigmatrix.rastreio.ui.theme.RastreioTheme
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel by inject<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RastreioTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RenderPackage(viewModel = viewModel)
                }
            }
        }
    }

}

@Composable
fun RenderPackage(viewModel: MainActivityViewModel) {
    val userPackage by viewModel.getPackageStatus("OP212763677BR").collectAsState(null)
    LazyColumn {
        items(userPackage?.statusUpdate.orEmpty()) { statusUpdate ->
            RenderPackageUpdate(statusUpdate)
        }
    }
}

@Composable
fun RenderPackageUpdate(statusUpdate: StatusUpdate) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = statusUpdate.description)
        Text(text = "Data: ${statusUpdate.date}")
        Text(text = "De: ${statusUpdate.from.localName}")
        Text(text = "Para: ${statusUpdate.to?.localName}")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RastreioTheme {
        Greeting("Android")
    }
}