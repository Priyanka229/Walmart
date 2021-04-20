package com.android.walmart.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.walmart.utilities.SingleLiveEvent
import com.android.walmart.utilities.WalmartLogger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

abstract class BaseWalmartVM<REPO: BaseWalmartRepository, INTER: BaseWalmartInteractor>(application: Application): AndroidViewModel(application) {
    lateinit var repository: REPO
    lateinit var interactor: INTER

    val uiBlockingLoaderLiveEvent = SingleLiveEvent<Boolean>() /** ui-blocking loader live-data */
    val showMsgLiveEvent = SingleLiveEvent<String>() /** msg live-data */

    protected val coroutineContext = Dispatchers.Default + CoroutineExceptionHandler{ _, th ->
        if (WalmartLogger.DEBUG) { th.printStackTrace() }
    }
}