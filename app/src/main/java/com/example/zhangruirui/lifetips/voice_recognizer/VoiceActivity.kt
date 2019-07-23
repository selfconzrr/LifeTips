package com.example.zhangruirui.lifetips.voice_recognizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import com.example.zhangruirui.lifetips.R
import kotlinx.android.synthetic.main.activity_voice.*

class VoiceActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "VoiceRecognition"
        private const val VOICE_RECOGNITION_REQUEST_CODE = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)
        google_sdk.setOnClickListener {
            googleVoiceDemo()
        }
    }

    private fun googleVoiceDemo() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请说标准普通话")
        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5) // 通常情况下，第一个结果是最准确的。
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
    }

    private fun baiduVoiceDemo() {

    }

    private fun aliVoiceDemo() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.toList()

            val stringBuilder = StringBuilder()

            matches?.forEach {
                stringBuilder.append(it).append("\n")
            }
            recognize_result.text = stringBuilder
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
