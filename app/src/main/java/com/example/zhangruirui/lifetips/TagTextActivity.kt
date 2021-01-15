package com.example.zhangruirui.lifetips

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.zhangruirui.lifetips.voice_recognizer.VoiceActivity
import kotlinx.android.synthetic.main.activity_tab_text.*

class TagTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_text)

        backMAin.setOnClickListener {
            val intent = Intent(this, VoiceActivity::class.java)
            startActivity(intent)
        }
        Log.e("zhangrr", "TagTextActivity onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.e("zhangrr", "TagTextActivity onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.e("zhangrr", "TagTextActivity onResume()")

    }

    override fun onPause() {
        super.onPause()
        Log.e("zhangrr", "TagTextActivity onPause()")

    }

    override fun onStop() {
        super.onStop()
        Log.e("zhangrr", "TagTextActivity onStop()")

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("zhangrr", "TagTextActivity onRestart()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("zhangrr", "TagTextActivity onDestroy()")

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.e("zhangrr", "TagTextActivity onSaveInstanceState()")

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("zhangrr", "TagTextActivity onRestoreInstanceState()")

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
