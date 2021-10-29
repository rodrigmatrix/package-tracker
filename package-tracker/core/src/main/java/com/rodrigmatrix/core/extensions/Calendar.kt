package com.rodrigmatrix.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    return null
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.parse(this)
}