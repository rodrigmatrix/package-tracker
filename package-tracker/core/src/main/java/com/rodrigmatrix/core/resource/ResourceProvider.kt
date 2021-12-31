package com.rodrigmatrix.core.resource

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes stringRes: Int, vararg params: String = emptyArray()): String

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable?
}