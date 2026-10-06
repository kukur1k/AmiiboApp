package com.example.amiiboapp.presentation.CreateNoteScreen

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun CreateCameraOutputUri(context: Context): Uri {
    var imageDir = File(context.cacheDir, "images").apply { mkdirs() }
    val photoFile = File(imageDir, "camera_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", photoFile)
}