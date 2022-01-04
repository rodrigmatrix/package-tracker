package com.rodrigmatrix.packagetracker.presentation.addpackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.DynamicColors
import com.rodrigmatrix.core.extensions.toast
import com.rodrigmatrix.packagetracker.R
import com.rodrigmatrix.packagetracker.databinding.FragmentAddNewPackageBinding
import com.rodrigmatrix.packagetracker.presentation.addpackage.AddPackageViewEffect.PackageAdded
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val PACKAGE_ID_EXTRA = "package_id_extra"

class AddNewPackageBottomSheetFragment: BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentAddNewPackageBinding.inflate(layoutInflater)
    }

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
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyIfAvailable(requireActivity())
        setObservers()
        binding.textButton.setOnClickListener {
            addPackage()
        }
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                viewState.error?.let {
                    toast(it)
                }
                if (viewState.isEditPackage) {
                    setEditView()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.viewEffect.collect {
                when (it) {
                    is PackageAdded -> {
                        dismiss()
                    }
                    is AddPackageViewEffect.SetPackageData -> {
                        setPackage(it.name, it.packageId)
                    }
                }
            }
        }
    }

    private fun setEditView() = with(binding) {
        textInputPackageId.isEnabled = false
        textButton.text = getString(R.string.edit)
        title.text = getString(R.string.edit_package)
    }

    private fun setPackage(name: String, packageId: String) = with(binding) {
        textInputName.setText(name)
        textInputPackageId.setText(packageId)
    }

    private fun addPackage() = with(binding) {
        val name = textInputName.text?.toString().orEmpty()
        val packageId = textInputPackageId.text?.toString().orEmpty()

        if (viewModel.viewState.value.isEditPackage) {
            viewModel.editPackage(name, packageId)
        } else {
            viewModel.addPackage(name, packageId)
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