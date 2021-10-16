package com.rodrigmatrix.rastreio.presentation.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.google.android.material.composethemeadapter.MdcTheme
import com.rodrigmatrix.rastreio.databinding.ActivityPackageBinding
import com.rodrigmatrix.rastreio.presentation.details.DetailsScreen
import com.rodrigmatrix.rastreio.presentation.details.PackagesDetailsViewModel
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PackageActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPackageBinding.inflate(layoutInflater)
    }

    private val packageId: String by lazy {
        intent.getStringExtra("package_id").orEmpty()
    }

    private val viewModel by viewModel<PackagesDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setScreen()
    }

    private fun setScreen() {
        binding.composeView.setContent {
            MdcTheme {
                DetailsScreen(packageId, viewModel)
            }
        }
    }

}