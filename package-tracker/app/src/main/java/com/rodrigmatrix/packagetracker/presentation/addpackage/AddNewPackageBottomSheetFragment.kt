package com.rodrigmatrix.packagetracker.presentation.addpackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.PackageAdded
import com.rodrigmatrix.packagetracker.presentation.theme.PackageTrackerTheme
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val PACKAGE_ID_EXTRA = "package_id_extra"

class AddNewPackageBottomSheetFragment: BottomSheetDialogFragment() {

    private val packageId: String by lazy {
        arguments?.getString(PACKAGE_ID_EXTRA).orEmpty()
    }

    private val viewModel by viewModel<AddNewPackageViewModel> {
        parametersOf(packageId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PackageTrackerTheme {
                    AddPackageScreen(packageId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(requireActivity())
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect {
                when (it) {
                    is PackageAdded -> {
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(packageId: String? = null): AddNewPackageBottomSheetFragment {
            return AddNewPackageBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(PACKAGE_ID_EXTRA, packageId)
                }
            }
        }
    }
}