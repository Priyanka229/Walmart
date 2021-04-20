package com.android.walmart.network

import com.android.walmart.R
import com.android.walmart.WalmartApp
import com.android.walmart.network.WalmartNetworkConnector.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class WalmartNetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!isNetworkAvailable(WalmartApp.instance)) {
            throw NoNetworkException()
        }
        return chain.proceed(request)
    }

    class NoNetworkException : IOException() {
        override val message: String
            get() = WalmartApp.instance.resources.getString(R.string.network_unavailable_message)
    }
}