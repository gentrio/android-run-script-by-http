package com.gentrio.runscriptdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gentrio.runscript.SocketClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.setOnClickListener {
            SocketClient.startWebSocket("ws://10.250.46.13:8222")
        }
    }
}
