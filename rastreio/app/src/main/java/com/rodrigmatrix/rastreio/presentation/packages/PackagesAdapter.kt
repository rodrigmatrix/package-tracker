package com.rodrigmatrix.rastreio.presentation.packages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.rodrigmatrix.core.recycler.DefaultDiffUtil
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.rastreio.databinding.RecyclerItemPackageBinding

class PackagesAdapter: ListAdapter<UserPackage, PackagesViewHolder>(DefaultDiffUtil()) {

    override fun onBindViewHolder(holder: PackagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagesViewHolder {
        return PackagesViewHolder(
            RecyclerItemPackageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}