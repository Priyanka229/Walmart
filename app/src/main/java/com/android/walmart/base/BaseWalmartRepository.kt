package com.android.walmart.base

import com.android.walmart.network.WalmartNetworkController
import kotlinx.coroutines.CancellationException

open class BaseWalmartRepository {
    @Throws(CancellationException::class)
    suspend inline fun <reified T>doGet(api: String, queryMap: Map<String, Any> = HashMap()) = WalmartNetworkController.doGet<T>(api, queryMap)
}