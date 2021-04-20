package com.android.walmart.utilities

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WalmartUtility {

    inline fun <reified T> toParse(jsonString: String?): T? {
        return try {
            Gson().fromJson<T>(jsonString, object : TypeToken<T?>() {}.type)
        } catch (ex: Exception) {
            null
        }
    }

    fun getVisibility(visibility : Boolean?) : Int {
        return if (visibility == true) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}