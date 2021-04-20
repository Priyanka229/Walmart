package com.android.walmart.utilities

import android.util.Log
import androidx.databinding.library.BuildConfig

object WalmartLogger {

    private const val TAG = "WalmartLogger"
    val DEBUG = BuildConfig.DEBUG

    fun d(tag: String = TAG, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }
}