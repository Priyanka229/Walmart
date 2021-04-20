package com.android.walmart.roomdb

import androidx.room.TypeConverter
import com.android.walmart.beans.Currency
import com.android.walmart.beans.Language
import com.google.gson.Gson

class CountryTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun toCurrency(value: String?): Currency? {
        return gson.fromJson(value, Currency::class.java)
    }

    @TypeConverter
    fun toString(value: Currency?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLanguage(value: String?): Language? {
        return gson.fromJson(value, Language::class.java)
    }

    @TypeConverter
    fun toString(value: Language?): String? {
        return gson.toJson(value)
    }
}