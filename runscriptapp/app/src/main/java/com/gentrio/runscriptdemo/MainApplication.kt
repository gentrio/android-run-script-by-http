package com.gentrio.runscriptdemo

import android.app.Application
import com.gentrio.runscript.JavaExecutor

/**
 * author: gentrio
 * created on: 2019-07-12
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        JavaExecutor.init(this)
    }
}