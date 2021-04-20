package com.android.walmart.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.walmart.beans.Country

@Database(entities = [Country::class], version = 1)
@TypeConverters(CountryTypeConverter::class)
abstract class CountryDataBase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        private var instance: CountryDataBase? = null

        @Synchronized
        fun getInstance(context: Context): CountryDataBase {
            if(instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CountryDataBase::class.java, "country_database.db").build()
            }
            return instance!!
        }
    }
}