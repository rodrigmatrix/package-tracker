package com.rodrigmatrix.data.repository

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.rodrigmatrix.domain.repository.ImageRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

private const val IMAGE_DIR = "imageDir"

class ImageRepositoryImpl(
    private val contextWrapper: ContextWrapper,
) : ImageRepository {

    override fun getImage(imagePath: String): Bitmap? {
        return try {
            val file = File(imagePath)
            BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun saveImage(imageUri: Uri): String? {
        var fileOutputStream: FileOutputStream? = null
        return try {
            val bitmap = when {
                Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                    contextWrapper.contentResolver,
                    imageUri,
                )
                else -> {
                    val source = ImageDecoder.createSource(contextWrapper.contentResolver, imageUri)
                    ImageDecoder.decodeBitmap(source)
                }
            }
            val directory = contextWrapper.getDir(IMAGE_DIR, Context.MODE_PRIVATE)
            val file = File(directory, UUID.randomUUID().toString() + ".png")
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(
                Bitmap.CompressFormat.PNG,
                100,
                fileOutputStream
            )
            file.path
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun deleteImage(imagePath: String) {
        try {
            val file = File(imagePath)
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}