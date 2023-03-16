package com.svox.pico

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log

class CheckVoiceData : Activity() {

    companion object {
        private val dataFiles = arrayOf(
            "de-DE_gl0_sg.bin", "de-DE_ta.bin",
            "en-GB_kh0_sg.bin", "en-GB_ta.bin",
            "en-US_lh0_sg.bin", "en-US_ta.bin",
            "es-ES_ta.bin", "es-ES_zl0_sg.bin",
            "fr-FR_nk0_sg.bin", "fr-FR_ta.bin",
            "it-IT_cm0_sg.bin", "it-IT_ta.bin"
        )

        private val dataFilesInfo = arrayOf(
            "deu-DEU", "deu-DEU",
            "eng-GBR", "eng-GBR",
            "eng-USA", "eng-USA",
            "spa-ESP", "spa-ESP",
            "fra-FRA", "fra-FRA",
            "ita-ITA", "ita-ITA"
        )

        private val supportedLanguages = arrayOf(
            "deu-DEU", "eng-GBR", "eng-USA", "spa-ESP", "fra-FRA", "ita-ITA"
        )
        private const val TAG = "CheckVoiceDataOverlay"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        var result = TextToSpeech.Engine.CHECK_VOICE_DATA_PASS
        var foundMatch = false
        val languageCountry = HashMap<String, Boolean>()
        val available = ArrayList<String>()
        val unavailable = ArrayList<String>()

        intent.extras?.getStringArrayList(TextToSpeech.Engine.EXTRA_CHECK_VOICE_DATA_FOR)?.forEach {
            if (it.isNotEmpty()) {
                languageCountry[it] = true
            }
        }

        // Check for files
        supportedLanguages.forEach { language ->
            if (languageCountry.isEmpty() || languageCountry.containsKey(language)) {
                available.add(language)
                foundMatch = true
            }
        }

        if (languageCountry.isNotEmpty() && !foundMatch) {
            result = TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL
        }

        // Put the root directory for the file data + the data filenames
        val returnData = Intent().apply {
            putExtra(
                TextToSpeech.Engine.EXTRA_VOICE_DATA_ROOT_DIRECTORY,
                "${filesDir.absoluteFile}/svox/"
            )
            putExtra(TextToSpeech.Engine.EXTRA_VOICE_DATA_FILES, dataFiles)
            putExtra(TextToSpeech.Engine.EXTRA_VOICE_DATA_FILES_INFO, dataFilesInfo)
            putStringArrayListExtra(TextToSpeech.Engine.EXTRA_AVAILABLE_VOICES, available)
            putStringArrayListExtra(TextToSpeech.Engine.EXTRA_UNAVAILABLE_VOICES, unavailable)
        }
        setResult(result, returnData)
        finish()
    }
}