package com.svox.pico

import android.app.Activity
import android.os.Bundle
import android.util.Log

class DownloadVoiceDataOverlay : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DownloadVoiceDataOverlay", "onCreate()")
        finish()
    }
}