package com.gentrio.runscript

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.animation.BounceInterpolator
import android.widget.TextView
import bsh.Interpreter
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.MoveType
import com.yhao.floatwindow.Screen

/**
 * author: gentrio
 * created on: 2019-07-12
 */
object JavaExecutor {

    val javaContext: Interpreter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
        Interpreter()
    }

    fun init(application: Application) {
        initPrefs(application)
        injectContext(application)
        initFloatView(application)
    }

    private fun initPrefs(application: Application) {
        RunScriptPrefs.init(application)
    }

    private fun injectContext(application: Application) {
        javaContext.set("application", application)
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity?) {
                javaContext.unset("activity")
            }

            override fun onActivityResumed(activity: Activity?) {
                javaContext.set("activity", activity)
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                javaContext.unset(activity?.javaClass?.simpleName)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                javaContext.set(activity?.javaClass?.simpleName, activity)
            }
        })
    }

    private fun initFloatView(application: Application) {
        val drawable = GradientDrawable()
        drawable.setColor(Color.parseColor("#04be03"))
        val radius = dp2px(4).toFloat()
        drawable.cornerRadii = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        val textView = TextView(application)
        textView.background = drawable
        val topBottom = dp2px(6)
        val leftRight = dp2px(16)
        textView.setPadding(leftRight, topBottom, leftRight, topBottom)
        textView.setTextColor(Color.WHITE)
        textView.gravity = Gravity.CENTER
        textView.textSize = 16f
        SocketClient.connectionState.observeForever {
            when (it) {
                ConnectionState.OPEN -> textView.text = "Connected"
                ConnectionState.CLOSE -> textView.text = "Closed"
                ConnectionState.FAILED -> textView.text = "Failed"
                else -> return@observeForever
            }
        }

        textView.setOnClickListener {
            val intent = Intent(application, SettingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            application.startActivity(intent)
        }

        FloatWindow
            .with(application.applicationContext)
            .setView(textView)
            .setX(dp2px(4))
            .setY(Screen.height, 0.9f)
            .setMoveType(MoveType.slide, dp2px(4), dp2px(4))
            .setMoveStyle(500, BounceInterpolator())
            .setDesktopShow(false)
            .build()
    }

    private fun dp2px(dpValue: Int): Int {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun execute(javaScript: String): String {
        val result = try {
            javaContext.eval(javaScript).toString()
        } catch (throwable: Throwable) {
            throwable.message
        }

        return result ?: ""
    }
}