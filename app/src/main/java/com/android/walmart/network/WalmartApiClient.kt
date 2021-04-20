package com.android.walmart.network

import com.android.walmart.utilities.WalmartLogger
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object WalmartApiClient {
    var headers =  ConcurrentHashMap<String, String>()

    private val httpClient =
            OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val original = chain.request()

                        val request = original.newBuilder()
                            .headers(getHeaders(headers))
                            .method(original.method, original.body)
                            .build()
                        chain.proceed(request)
                    })
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(WalmartNetworkInterceptor())
                    .apply {
                        if (WalmartLogger.DEBUG) addInterceptor(
                            WalmartNetworkLoggingIntercepter()
                        )
                    }
                    .retryOnConnectionFailure(true)
                    .build()

    private val retrofit  = Retrofit.Builder()
            .addConverterFactory(WalmartNullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WalmartNetworkConstants.URL)
            .client(httpClient)
            .build()

    var service: WalmartApiService = retrofit.create(WalmartApiService::class.java)

    private fun getHeaders(map: ConcurrentHashMap<String, String>): Headers {
        val builder = Headers.Builder()
        for ((key, value) in map) {
            builder.add(key, value)
        }
        return builder.build()
    }
}