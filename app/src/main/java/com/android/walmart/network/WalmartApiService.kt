package com.android.walmart.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

@JvmSuppressWildcards
interface WalmartApiService {
    @GET
    suspend fun <T> get(@Url url: String, @QueryMap queryMap: Map<String, Any> = HashMap()): Response<T>
}