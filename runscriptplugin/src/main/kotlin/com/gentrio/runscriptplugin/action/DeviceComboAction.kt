package com.gentrio.runscriptplugin.action

import com.gentrio.runscriptplugin.Constants
import com.gentrio.runscriptplugin.icon.Icons
import com.gentrio.runscriptplugin.websoket.WebSocketService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.openapi.project.DumbAwareAction
import javax.swing.JComponent

/**
 * author: gentrio
 * created on: 2019-07-16
 */
class DeviceComboAction: ComboBoxAction() {

    init {
        templatePresentation.text = "none"
    }

    override fun update(e: AnActionEvent) {
        if (WebSocketService.selectedSocket != null) {
            e.presentation.text = WebSocketService.socketMap[WebSocketService.selectedSocket!!]
        } else {
            e.presentation.text = "none"
        }
    }

    override fun createPopupActionGroup(button: JComponent?): DefaultActionGroup {
        val actions = mutableListOf<DumbAwareAction>(object : DumbAwareAction("Active Adb Device", null, Icons.AdbService) {
            override fun actionPerformed(e: AnActionEvent) {
                ProcessHandlerFactory.getInstance().createProcessHandler(
                    GeneralCommandLine(Constants.ADB_START_WEBSOCKET.split(" "))
                )
            }
        })
        actions.addAll(WebSocketService.socketMap.map { entry ->
            object : DumbAwareAction(entry.value, null, Icons.Active) {
                override fun actionPerformed(e: AnActionEvent) {
                    WebSocketService.selectedSocket = entry.key
                }
            }
        })
        return DefaultActionGroup(actions)
    }
}