package com.android.walmart.network

import okhttp3.Interceptor
import okhttp3.Response

class WalmartNetworkLoggingIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        //TODO logging here

        return chain.proceed(request)
    }
}