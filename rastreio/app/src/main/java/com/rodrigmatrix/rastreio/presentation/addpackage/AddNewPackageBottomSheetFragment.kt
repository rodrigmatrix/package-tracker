package com.rodrigmatrix.rastreio.presentation.addpackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rodrigmatrix.rastreio.databinding.FragmentAddNewPackageBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewPackageBottomSheetFragment: BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentAddNewPackageBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<AddNewPackageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        binding.textButton.setOnClickListener {
            addPackage()
        }
        binding.textInputPackageId.setText("SF6041112550171")
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.viewState.collect {
                Toast.makeText(context, "${it.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPackage() = with(binding) {
        val name = textInputName.text?.toString().orEmpty()
        val packageId = textInputPackageId.text?.toString().orEmpty()

        viewModel.addPackage(name, packageId)
    }

}