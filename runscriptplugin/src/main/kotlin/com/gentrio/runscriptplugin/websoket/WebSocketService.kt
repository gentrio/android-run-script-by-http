package com.gentrio.runscriptplugin.websoket

import com.gentrio.runscriptplugin.Constants
import com.gentrio.runscriptplugin.util.executeOnPooledThread
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.components.ProjectComponent
import org.java_websocket.WebSocket
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress

/**
 * author: gentrio
 * created on: 2019-07-15
 */
class WebSocketService: ApplicationComponent{

    companion object{
        val socketMap = HashMap<WebSocket, String>()
        var socketServer: WebSocketServer? = null
        var selectedSocket: WebSocket? = null
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