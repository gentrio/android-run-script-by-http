package com.gentrio.runscriptplugin.websoket

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

/**
 * author: gentrio
 * created on: 2019-07-15
 */
class RunWsServer(inetAddress: InetSocketAddress) : WebSocketServer(inetAddress) {

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        println("onOpen")
        val name = handshake?.getFieldValue("Device")
        if (conn != null && name != null) {
            WebSocketService.socketMap[conn] = name
            if (WebSocketService.selectedSocket == null) {
                WebSocketService.selectedSocket = conn
            }
        }
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String, remote: Boolean) {
        println("onClose")
        if (conn != null) {
            WebSocketService.socketMap.remove(conn)
            if (conn == WebSocketService.selectedSocket) {
                WebSocketService.selectedSocket = null
            }
        }
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        println("onMessage")
    }

    override fun onStart() {
        println("onStart")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("onError")
    }
}