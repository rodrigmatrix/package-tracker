package com.rodrigmatrix.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface ImageRepository {

    fun saveImage(imageUri: Uri): String?

    fun getImage(imagePath: String): Bitmap?

    fun deleteImage(imagePath: String)
}