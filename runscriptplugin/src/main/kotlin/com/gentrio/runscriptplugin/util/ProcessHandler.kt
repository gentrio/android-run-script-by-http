package com.gentrio.runscriptplugin.util

import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessOutputTypes

fun ProcessHandler.stdout(text: String) {
    notifyTextAvailable("$text\n", ProcessOutputTypes.STDOUT)
}

fun ProcessHandler.stderr(text: String) {
    notifyTextAvailable("$text\n", ProcessOutputTypes.STDERR)
}

fun ProcessHandler.system(text: String) {
    notifyTextAvailable("$text\n", ProcessOutputTypes.SYSTEM)
}

fun ProcessHandler.finish() {
    notifyTextAvailable("Process finished with exit code $exitCode\n", ProcessOutputTypes.SYSTEM)
}