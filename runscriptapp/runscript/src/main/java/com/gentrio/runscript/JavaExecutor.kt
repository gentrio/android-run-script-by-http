package com.gentrio.runscript

import android.app.Activity
import android.app.Application
import android.os.Bundle
import bsh.Interpreter

/**
 * author: gentrio
 * created on: 2019-07-12
 */
object JavaExecutor {

    val javaContext: Interpreter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
        Interpreter()
    }

    fun init(application: Application) {
        javaContext.set("application", application)
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                javaContext.unset(activity?.localClassName)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                javaContext.set(activity?.localClassName, activity)
            }
        })
    }

    fun execute(javaScript: String): String {
        var result: String?
        result = try {
            javaContext.eval(javaScript).toString()
        } catch (throwable: Throwable) {
            throwable.message
        }
        return result ?: ""
    }
}