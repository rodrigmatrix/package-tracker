package com.rodrigmatrix.core.recycler

import androidx.recyclerview.widget.DiffUtil

class DefaultDiffUtil<T>: DiffUtil.ItemCallback<T>() {

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}