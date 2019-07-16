package com.gentrio.runscript

import android.os.Build
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * author: gentrio
 * created on: 2019-07-12
 */
object SocketClient {

    val okHttpClient = OkHttpClient()

    var webSocket: WebSocket? = null

    var connectionState: MutableLiveData<ConnectionState> = MutableLiveData()

    init {
        connectionState.value = ConnectionState.CLOSE
    }

    fun startWebSocket(url: String) {
        if (webSocket != null && connectionState.value == ConnectionState.OPEN) {
            return
        }

        RunScriptPrefs.instance?.setUrl(url)
        val request = Request.Builder().url(url).addHeader("Device", "${Build.BRAND} ${Build.MODEL} API ${Build.VERSION.SDK_INT}").build()
        webSocket = okHttpClient.newWebSocket(request, SocketListener())
    }

    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    fun closeWebSocket() {
        webSocket?.close(88, "bye bye")
        connectionState.value = ConnectionState.CLOSE
        webSocket = null
    }
}