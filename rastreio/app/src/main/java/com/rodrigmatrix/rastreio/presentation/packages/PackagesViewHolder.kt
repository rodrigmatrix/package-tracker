package com.rodrigmatrix.rastreio.presentation.packages

import androidx.recyclerview.widget.RecyclerView
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.databinding.RecyclerItemPackageBinding
import com.rodrigmatrix.rastreio.extensions.getLastStatus

class PackagesViewHolder(
    private val binding: RecyclerItemPackageBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(userPackage: UserPackage) = with(binding) {

        val lastStatus = userPackage.getLastStatus()

        packageNameText.text = userPackage.name
        packageIdText.text = userPackage.packageId
    }
}