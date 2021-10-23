package com.rodrigmatrix.rastreio.presentation.packages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rodrigmatrix.rastreio.databinding.ActivityHomeBinding
import com.rodrigmatrix.rastreio.presentation.history.PackageActivity
import com.google.android.material.composethemeadapter.MdcTheme
import com.rodrigmatrix.rastreio.presentation.addpackage.AddNewPackageBottomSheetFragment
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PackagesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val packagesAdapter by lazy {
        PackagesAdapter()
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
            viewModel.viewState.collect { viewState ->
                packagesAdapter.submitList(viewState.packagesList)
            }
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
            .show(supportFragmentManager, PackagesActivity::class.simpleName)
    }

    private fun openPackageScreen(packageId: String) {
        startActivity(
            Intent(this, PackageActivity::class.java)
                .putExtra("package_id", packageId)
        )
    }
}