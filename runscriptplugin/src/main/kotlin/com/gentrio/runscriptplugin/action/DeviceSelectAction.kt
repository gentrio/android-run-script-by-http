package com.gentrio.runscriptplugin.action

import com.gentrio.runscriptplugin.icon.Icons
import com.gentrio.runscriptplugin.websoket.WebSocketService
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import org.java_websocket.WebSocket

/**
 * author: gentrio
 * created on: 2019-08-01
 */
class DeviceSelectAction :
    DumbAwareAction {

    constructor() : super()

    constructor(text: String) : super(text, null, Icons.Active)

    var webSocket: WebSocket? = null

    override fun actionPerformed(e: AnActionEvent) {
        if (webSocket != null) {
            WebSocketService.selectedSocket = webSocket
        }
    }

}