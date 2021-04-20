package com.android.walmart

import android.app.Application

class WalmartApp: Application() {
    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}