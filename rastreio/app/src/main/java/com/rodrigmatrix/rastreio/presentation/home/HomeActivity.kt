package com.rodrigmatrix.rastreio.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rodrigmatrix.rastreio.databinding.ActivityHomeBinding
import com.rodrigmatrix.rastreio.presentation.history.PackageActivity
import com.rodrigmatrix.rastreio.presentation.packages.PackagesScreen
import com.rodrigmatrix.rastreio.presentation.packages.PackagesViewEffect
import com.rodrigmatrix.rastreio.presentation.packages.PackagesViewModel
import com.google.android.material.composethemeadapter.MdcTheme
import com.rodrigmatrix.rastreio.presentation.addpackage.AddNewPackageBottomSheetFragment
import com.rodrigmatrix.rastreio.presentation.theme.RastreioTheme
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<PackagesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setObservers()
        setScreen()
    }

    private fun setScreen() {
        binding.composeView.setContent {
            MdcTheme {
                PackagesScreen(viewModel)
            }
        }
        binding.addPackageFab.setOnClickListener {
            openAddPackageFragment()
        }
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect { effect ->
                when (effect) {
                    is PackagesViewEffect.OpenPackageScreen -> {
                        openPackageScreen(effect.packageId)
                    }
                }
            }
        }
    }

    private fun openAddPackageFragment() {
        AddNewPackageBottomSheetFragment()
            .show(supportFragmentManager, HomeActivity::class.simpleName)
    }

    private fun openPackageScreen(packageId: String) {
        startActivity(
            Intent(this, PackageActivity::class.java)
                .putExtra("package_id", packageId)
        )
    }
}