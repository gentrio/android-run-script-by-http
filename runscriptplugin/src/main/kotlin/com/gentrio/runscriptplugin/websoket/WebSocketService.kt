package com.gentrio.runscriptplugin.websoket

import com.gentrio.runscriptplugin.Constants
import com.gentrio.runscriptplugin.util.executeOnPooledThread
import com.intellij.openapi.components.ProjectComponent
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

/**
 * author: gentrio
 * created on: 2019-07-15
 */
class WebSocketService: ProjectComponent{

    companion object{
        var socketServer: WebSocketServer? = null
        val socketMap = HashMap<WebSocket, String>()
    }


    override fun initComponent() {
        super.initComponent()
        executeOnPooledThread {
            socketServer = RunWsServer(InetSocketAddress(Constants.WEBSOCKET_PORT))
            socketServer?.run()
        }
    }

    override fun disposeComponent() {
        super.disposeComponent()
        socketServer?.stop()
    }
}