package com.svox.pico

import android.content.Context
import java.io.File

object FileUtils {
    fun copyFromAssets(context: Context, fileName: String, output: File) {
        context.assets.open(fileName).use { input ->
            output.outputStream().use {
                input.copyTo(it)
            }
        }
    }
}