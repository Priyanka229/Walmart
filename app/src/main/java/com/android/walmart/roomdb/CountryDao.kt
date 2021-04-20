package com.android.walmart.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.walmart.beans.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(countries: List<Country>)

    @Query("select * from country_table order by name")
    fun getCountries() : LiveData<List<Country>>
}