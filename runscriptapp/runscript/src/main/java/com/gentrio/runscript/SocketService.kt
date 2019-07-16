package com.gentrio.runscript

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * author: gentrio
 * created on: 2019-07-12
 */
class SocketService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val url = intent.data?.getQueryParameter("url")
        if (url != null) {
            SocketClient.startWebSocket(url)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}