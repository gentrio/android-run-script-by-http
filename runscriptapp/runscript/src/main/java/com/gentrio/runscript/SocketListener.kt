package com.gentrio.runscript

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

/**
 * author: gentrio
 * created on: 2019-07-15
 */
class SocketListener: WebSocketListener() {

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        AppExecutors.instance.mainThread().execute {
            SocketClient.connectionState.value = ConnectionState.CLOSE
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        AppExecutors.instance.mainThread().execute{
            SocketClient.connectionState.value = ConnectionState.FAILED
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        JavaExecutor.execute(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        AppExecutors.instance.mainThread().execute {
            SocketClient.connectionState.value = ConnectionState.OPEN
        }
    }
}