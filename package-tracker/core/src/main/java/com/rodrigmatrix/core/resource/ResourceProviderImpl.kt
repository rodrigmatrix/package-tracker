package com.rodrigmatrix.core.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourceProviderImpl(
    private val applicationContext: Context
) : ResourceProvider {

    override fun getString(stringRes: Int, vararg params: String): String {
        return applicationContext.getString(stringRes, *params)
    }

    override fun getDrawable(drawableRes: Int): Drawable? {
        return ContextCompat.getDrawable(applicationContext, drawableRes)
    }
}