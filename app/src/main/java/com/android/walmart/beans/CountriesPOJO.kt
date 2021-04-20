package com.android.walmart.beans

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "country_table")
@Parcelize
data class Country(
        @ColumnInfo(name = "capital") @SerializedName("capital") val capital: String?,
        @ColumnInfo(name = "code") @SerializedName("code") val code: String?,
        @ColumnInfo(name = "currency") @SerializedName("currency") val currency: Currency?,
        @ColumnInfo(name = "flag") @SerializedName("flag") val flag: String?,
        @ColumnInfo(name = "language") @SerializedName("language") val language: Language?,
        @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "name") @SerializedName("name") val name: String,
        @ColumnInfo(name = "region") @SerializedName("region") val region: String?
): Parcelable

@Parcelize
data class Currency(
        @ColumnInfo(name = "code") @SerializedName("code") val code: String?,
        @ColumnInfo(name = "name") @SerializedName("name") val name: String?,
        @ColumnInfo(name = "symbol") @SerializedName("symbol") val symbol: String?
): Parcelable

@Parcelize
data class Language(
        @ColumnInfo(name = "code") @SerializedName("code") val code: String?,
        @ColumnInfo(name = "name") @SerializedName("name") val name: String?
): Parcelable