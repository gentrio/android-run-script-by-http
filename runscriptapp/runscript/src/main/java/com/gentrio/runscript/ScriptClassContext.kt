package com.gentrio.runscript

import android.app.Activity
import android.app.Application

/**
 * author: gentrio
 * created on: 2019-07-16
 */
open class ScriptClassContext {

    @JvmField
    protected var application: Application? = null

    @JvmField
    protected var activity: Activity? = null
}