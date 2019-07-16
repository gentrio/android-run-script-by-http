package com.gentrio.runscript

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * author: gentrio
 * created on: 2019-07-16
 */
class SettingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        edit.setText(RunScriptPrefs.instance?.getUrl())
        val intent = Intent(this, SocketService::class.java)
        intent.data = Uri.parse("gentrio://runscript?url=${RunScriptPrefs.instance?.getUrl()}")
        button.setOnClickListener {
            startService(intent)
            finish()
        }

        outside.setOnClickListener {
            finish()
        }
    }
}