package com.svox.pico.providers

import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Environment

class SettingsProviderOverlay : SettingsProvider() {
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val cursor = SettingsCursor()
        cursor.settings = context?.let {
            it.filesDir.toString() + "/svox/"
        } ?: run {
            Environment.getExternalStorageDirectory().toString() + "/svox/"
        }
        return cursor
    }
}

class SettingsCursor(var settings: String? = null) : MatrixCursor(arrayOf("", "")) {
    override fun getCount(): Int = 1
    override fun getString(column: Int) = settings ?: ""
}