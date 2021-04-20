package com.android.walmart.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.walmart.WalmartApp
import com.android.walmart.beans.Country
import com.android.walmart.network.WalmartError
import com.android.walmart.network.WalmartResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountryVMTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: CountryRepository

    @Mock
    private lateinit var interactor: CountryInteractor

    @Mock
    private lateinit var msgLiveEventObserver: Observer<String>
    @Mock
    private lateinit var countryListLiveDataObserver: Observer<CountryVM.CountryData>

    @Before
    fun setUp() {
//        MockitoAnnotations.initMocks(this)
        // do something if required
    }

    /** test the serve response in case of success */
    @Test
    fun `test the server response in case of success`() {
        testCoroutineRule.runBlockingTest {
            doReturn(WalmartResponse<List<Country>>(requestType = "test", data = emptyList()))
                .`when`(repository)
                .getCountries()

            val applicationMock = WalmartApp()
            val viewModel = CountryVM(applicationMock)
            viewModel.countryLiveData.observeForever(countryListLiveDataObserver)

            val output = repository.getCountries().data
            viewModel.countryLiveData.postValue(CountryVM.CountryData.Success(output!!))

            viewModel.countryLiveData.removeObserver(countryListLiveDataObserver)

            verify(repository).getCountries()
        }
    }

    /** test the serve response in case of failure */
    @Test
    fun `test the server response in case of failure`() {
        testCoroutineRule.runBlockingTest {
            doReturn(WalmartResponse<List<Country>>(requestType = "test", error = WalmartError("something went wrong")))
                .`when`(repository)
                .getCountries()

            val applicationMock = WalmartApp()
            val viewModel = CountryVM(applicationMock)
            viewModel.showMsgLiveEvent.observeForever(msgLiveEventObserver)

            val output = repository.getCountries().error?.msg
            viewModel.showMsgLiveEvent.postValue(output)

            viewModel.showMsgLiveEvent.removeObserver(msgLiveEventObserver)

            verify(repository).getCountries()
            verify(msgLiveEventObserver).onChanged("something went wrong")
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }
}