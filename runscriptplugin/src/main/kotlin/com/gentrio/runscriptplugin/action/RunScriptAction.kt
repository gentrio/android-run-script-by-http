package com.gentrio.runscriptplugin.action

import com.gentrio.runscriptplugin.Constants
import com.gentrio.runscriptplugin.Constants.ADB_START_RUN_SCRIPT
import com.gentrio.runscriptplugin.file.JavaFileExtract
import com.gentrio.runscriptplugin.icon.Icons
import com.gentrio.runscriptplugin.util.executeOnPooledThread
import com.gentrio.runscriptplugin.util.finish
import com.gentrio.runscriptplugin.util.stderr
import com.gentrio.runscriptplugin.util.system
import com.gentrio.runscriptplugin.websoket.WebSocketService
import com.intellij.execution.RunContentExecutor
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project

class RunScriptAction : AnAction(Icons.Run), DumbAware {


    init {
        templatePresentation.disabledIcon = Icons.RunDisable
        templatePresentation.icon = Icons.Run
        templatePresentation.isEnabled = false
    }

    private lateinit var project: Project
    private lateinit var execute: RunContentExecutor
    private lateinit var processHandler: ProcessHandler
    private lateinit var javaFileExtract: JavaFileExtract

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = WebSocketService.selectedSocket != null
    }

    override fun actionPerformed(e: AnActionEvent) {

        if (e.project != null) {
            project = e.project!!
            //Run Window初始化 借助命令执行器
            processHandler = ProcessHandlerFactory.getInstance().createProcessHandler(
                GeneralCommandLine(ADB_START_RUN_SCRIPT.split(" "))
            )
            val selectedFile = FileEditorManager.getInstance(project).selectedEditor?.file
            //提取Java文件代码
            javaFileExtract = JavaFileExtract(project, selectedFile)

            execute = RunContentExecutor(
                project, processHandler
            ).withTitle(selectedFile?.name ?: Constants.DEFAULT_RUN_WINDOW_NAME)
                .withRerun {
                    remoteRun()
                }

            remoteRun()
        }
    }

    private fun remoteRun() {
        execute.run()
        ApplicationManager.getApplication().invokeLater {
            processHandler.notifyTextAvailable("\n\n", ProcessOutputTypes.SYSTEM)
            try {
                val script = javaFileExtract.getScript()
                if (script != null) {
                    //发送script到server端
                    //输出返回数据到Run Console
                    executeOnPooledThread {
                        WebSocketService.selectedSocket?.send(script)
                    }
                } else {
                    processHandler.stderr("Not found @ScriptClass && @Script or script is empty !!!")
                    processHandler.finish()
                }
            } catch (e: Exception) {
                //输出到Run Console
                if (e.message != null) {
                    processHandler.system(e.message!!)
                    processHandler.finish()
                }
            }
        }
    }
}