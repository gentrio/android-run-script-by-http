package com.gentrio.runscriptplugin.util

import com.intellij.openapi.application.ApplicationManager
import java.util.concurrent.Future

/**
 * Requests pooled thread to execute the [action].
 */
inline fun executeOnPooledThread(crossinline action: () -> Unit)
        : Future<*> = ApplicationManager.getApplication().executeOnPooledThread { action() }

/**
 * Asynchronously execute the [action] on the AWT event dispatching thread.
 */
inline fun invokeLater(crossinline action: () -> Unit) {
    ApplicationManager.getApplication().invokeLater { action() }
}