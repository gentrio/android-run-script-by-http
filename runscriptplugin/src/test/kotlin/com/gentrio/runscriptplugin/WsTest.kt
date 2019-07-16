package com.gentrio.runscriptplugin

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.InetAddress
import java.net.URI

/**
 * author: gentrio
 * created on: 2019-07-15
 */

fun main(args: Array<String>) {

    val hostAddress = InetAddress.getLocalHost().hostAddress
    EmptyClient(URI("ws://$hostAddress:${Constants.WEBSOCKET_PORT}")).connect()

}

class EmptyClient(uri: URI) : WebSocketClient(uri) {
    override fun onOpen(handshakedata: ServerHandshake?) {
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
    }

    override fun onMessage(message: String?) {
    }

    override fun onError(ex: Exception?) {

    }

}