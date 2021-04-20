package com.android.walmart.network

import com.android.walmart.utilities.WalmartUtility
import com.android.walmart.R
import com.android.walmart.WalmartApp
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object WalmartNetworkController {
    suspend inline fun <reified T> doGet(api: String, queryMap: Map<String, Any> = HashMap()): WalmartResponse<T> {
        return try {
            val apiResponse = WalmartApiClient.service.get<LinkedTreeMap<String, String>>(api, queryMap)
            handleApiResponse(api, apiResponse)
        } catch (ex: Exception) {
            val error = handleApiCallExceptions(ex)
            WalmartResponse(requestType = api, error = error)
        }
    }

    inline fun <reified T> handleApiResponse(requestType: String, response: Response<LinkedTreeMap<String, String>>): WalmartResponse<T> {
        val returnValue: WalmartResponse<T>

        when {
            response.isSuccessful -> {
                val gsonStr = Gson().toJson(response.body())
                val responsePOJO = WalmartUtility.toParse<T>(gsonStr)
                returnValue = WalmartResponse(requestType = requestType, data = responsePOJO)
            }

            else -> {
                var error: WalmartError? = null
                val errorString = if (response.errorBody() != null) response.errorBody()!!.string() else null
                if (errorString != null) {
                    error = Gson().fromJson(errorString, WalmartError::class.java)
                }

                if (error == null) { error = WalmartError(msg = WalmartApp.instance.resources.getString(
                    R.string.something_went_wrong)) }

                returnValue = WalmartResponse(requestType = requestType, error = error)
            }
        }

        return returnValue
    }

    fun handleApiCallExceptions(ex: Exception): WalmartError {
        return when(ex) {
            is WalmartNetworkInterceptor.NoNetworkException -> WalmartError(msg = WalmartApp.instance.resources.getString(R.string.network_unavailable_message))
            is JsonParseException -> WalmartError(msg = WalmartApp.instance.resources.getString(R.string.parse_error))
            is SocketTimeoutException -> WalmartError(msg = WalmartApp.instance.resources.getString(R.string.socket_time_out))
            is UnknownHostException -> WalmartError(msg = WalmartApp.instance.resources.getString(R.string.something_went_wrong))
            else -> throw ex
        }
    }
}

data class WalmartResponse <T> (
    val requestType: String,
    val data: T? = null,
    val error: WalmartError? = null
)
data class WalmartError(
    val msg: String
)
