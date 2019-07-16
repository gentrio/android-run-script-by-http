package com.gentrio.runscriptplugin

import java.net.InetAddress

object Constants {

    const val DEFAULT_RUN_WINDOW_NAME = "Run Script"

    const val WEBSOCKET_PORT = 8222

    val ADB_FORWARD_TERMINAL =
        "adb shell am startservice -d \"gentrio://runscript?url=ws://${InetAddress.getLocalHost().hostAddress}:$WEBSOCKET_PORT\""

    const val CLASS_ANNOTATION = "@ScriptClass"
    const val METHOD_ANNOTATION = "@Script"


}