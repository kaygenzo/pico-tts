package com.svox.pico

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import java.io.File

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

    private var PICO_LINGWARE_PATH: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate()")

        PICO_LINGWARE_PATH = "${filesDir.absolutePath}/svox/"

        var result = TextToSpeech.Engine.CHECK_VOICE_DATA_PASS
        var foundMatch = false
        val languageCountry = HashMap<String, Boolean>()
        val available = ArrayList<String>()
        val unavailable = ArrayList<String>()

        val outputVoicesDir = File(PICO_LINGWARE_PATH)
        if (!outputVoicesDir.exists()) {
            outputVoicesDir.mkdirs()
        }

        intent.extras?.getStringArrayList(TextToSpeech.Engine.EXTRA_CHECK_VOICE_DATA_FOR)?.forEach {
            if (it.isNotEmpty()) {
                languageCountry[it] = true
            }
        }

        // Check for files
        supportedLanguages.forEachIndexed { i, language ->
            if (languageCountry.isEmpty() || languageCountry.containsKey(language)) {
                if (!fileExists(dataFiles[2 * i]) || !fileExists(dataFiles[2 * i + 1])) {
                    FileUtils.copyFromAssets(
                        this,
                        dataFiles[2 * i],
                        File(outputVoicesDir, dataFiles[2 * i])
                    )
                    FileUtils.copyFromAssets(
                        this,
                        dataFiles[2 * i + 1],
                        File(outputVoicesDir, dataFiles[2 * i + 1])
                    )
                }
                available.add(language)
                foundMatch = true
            }
        }

        if (languageCountry.isNotEmpty() && !foundMatch) {
            result = TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL
        }

        // Put the root directory for the file data + the data filenames
        val returnData = Intent().apply {
            putExtra(TextToSpeech.Engine.EXTRA_VOICE_DATA_ROOT_DIRECTORY, PICO_LINGWARE_PATH)
            putExtra(TextToSpeech.Engine.EXTRA_VOICE_DATA_FILES, dataFiles)
            putExtra(TextToSpeech.Engine.EXTRA_VOICE_DATA_FILES_INFO, dataFilesInfo)
            putStringArrayListExtra(TextToSpeech.Engine.EXTRA_AVAILABLE_VOICES, available)
            putStringArrayListExtra(TextToSpeech.Engine.EXTRA_UNAVAILABLE_VOICES, unavailable)
        }
        setResult(result, returnData)
        finish()
    }

    private fun fileExists(filename: String): Boolean {
        val tempFile = File(PICO_LINGWARE_PATH + filename)
        return tempFile.exists()
    }
}