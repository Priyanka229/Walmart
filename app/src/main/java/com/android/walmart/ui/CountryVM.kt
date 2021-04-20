package com.android.walmart.ui

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.android.walmart.R
import com.android.walmart.base.BaseWalmartVM
import com.android.walmart.beans.Country
import com.android.walmart.roomdb.CountryDataBase
import com.android.walmart.utilities.EspressoIdlingResource
import kotlinx.coroutines.launch

class CountryVM(val app: Application) : BaseWalmartVM<CountryRepository, CountryInteractor>(app) {
    val countryLiveData = MediatorLiveData<CountryData>()

    var countries: List<Country>? = null

    init {
        repository = CountryRepository()
        interactor = CountryInteractor()
    }

    sealed class CountryData{
        class Success(val data: List<Country>): CountryData()
        class NoData(val msg: String) : CountryData()
        class Retry(val msg: String, val actionText: String): CountryData()
    }

    fun countryListScreenLaunched() {
        databaseObserver()
        fetchCountries()
    }

    private fun databaseObserver() {
        val countryLiveData = CountryDataBase.getInstance(app).countryDao().getCountries()
        this.countryLiveData.addSource(countryLiveData) {
            val data =
                    if (it.isNullOrEmpty())
                        CountryData.NoData(app.resources.getString(R.string.no_data))
                    else
                        CountryData.Success(it)

            this.countryLiveData.postValue(data)
        }
    }

    fun fetchCountries() {
        /** fetch the signed in user response */
        viewModelScope.launch(coroutineContext) {
            EspressoIdlingResource.increment()

            /** show loader */
            uiBlockingLoaderLiveEvent.postValue(true)

            /** reading from server */
            val output = repository.getCountries()
            countries = output.data

            if (output.data != null) {
                val countries = output.data

                CountryDataBase.getInstance(app).countryDao().insert(countries)

            } else {
                val retryText = output.error?.msg
                val retryActionText = app.resources.getString(R.string.retry_action_text)

                if (retryText != null && retryText.isNotBlank() && retryActionText.isNotBlank()) {
                    val retryData = CountryData.Retry(retryText, retryActionText)
                    countryLiveData.postValue(retryData)
                }
            }

            /** hide loader */
            uiBlockingLoaderLiveEvent.postValue(false)

            EspressoIdlingResource.decrement()
        }
    }
}