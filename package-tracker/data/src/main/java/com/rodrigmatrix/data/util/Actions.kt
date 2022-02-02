package com.rodrigmatrix.data.util

import android.content.Context
import android.content.Intent

fun mainActivityIntent(context: Context) =
    internalIntent(context, "action.packagetracker.open")

private fun internalIntent(context: Context, action: String): Intent {
    return Intent(action).setPackage(context.packageName)
}
