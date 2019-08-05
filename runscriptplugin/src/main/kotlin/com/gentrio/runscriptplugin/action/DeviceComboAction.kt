package com.gentrio.runscriptplugin.action

import com.gentrio.runscriptplugin.websoket.WebSocketService
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.ex.ComboBoxAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbAwareAction
import javax.swing.JComponent

/**
 * author: gentrio
 * created on: 2019-07-16
 */
class DeviceComboAction : ComboBoxAction(), DumbAware {

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
        val actions =
            mutableListOf<DumbAwareAction>(AdbActiveAction("Active Adb Device"))
        actions.addAll(WebSocketService.socketMap.map { entry ->
            DeviceSelectAction(entry.value).apply {
                webSocket = entry.key
            }
        })
        return DefaultActionGroup(actions)

    }
}