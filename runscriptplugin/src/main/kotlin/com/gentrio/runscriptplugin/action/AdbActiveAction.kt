package com.gentrio.runscriptplugin.action

import com.gentrio.runscriptplugin.Constants
import com.gentrio.runscriptplugin.icon.Icons
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

/**
 * author: gentrio
 * created on: 2019-08-01
 */
class AdbActiveAction :
    DumbAwareAction {

    constructor() : super()

    constructor(text: String) : super(text, null, Icons.AdbService)


    override fun actionPerformed(e: AnActionEvent) {
        ProcessHandlerFactory.getInstance().createProcessHandler(
            GeneralCommandLine(Constants.ADB_START_WEBSOCKET.split(" "))
        )
    }

}