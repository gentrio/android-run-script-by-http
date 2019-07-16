package com.gentrio.runscript

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * author: gentrio
 * created on: 2019-07-16
 */
class RunScriptPrefs private constructor(application: Application) {

    var mSp: SharedPreferences = application.getSharedPreferences("RunScriptPrefs", Context.MODE_PRIVATE)

    companion object {

        var instance: RunScriptPrefs? = null

        fun init(application: Application) {
            instance = RunScriptPrefs(application)
        }
    }

    fun setUrl(url: String) {
        mSp.edit().putString("url", url).apply()
    }

    fun getUrl(): String {
        return mSp.getString("url", "")
    }

}