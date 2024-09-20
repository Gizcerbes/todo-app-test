package com.uogames.salesautomators.test

import android.app.Application

class Application : Application() {


    companion object {

        private var _INSTANCE: Application? = null
        val appContext get() = _INSTANCE!!.applicationContext

    }

    override fun onCreate() {
        _INSTANCE = this
        super.onCreate()
    }

    override fun onTerminate() {
        _INSTANCE = null
        super.onTerminate()
    }

}