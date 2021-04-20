package com.android.walmart.ui

import com.android.walmart.base.BaseWalmartRepository
import com.android.walmart.beans.Country
import com.android.walmart.network.WalmartNetworkConstants.COUNTIRES
import com.android.walmart.network.WalmartResponse
import com.android.walmart.utilities.OpenForTesting

@OpenForTesting
class CountryRepository: BaseWalmartRepository() {
    suspend fun getCountries(): WalmartResponse<List<Country>> = doGet(COUNTIRES)
}