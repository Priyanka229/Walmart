package com.android.walmart

import com.android.walmart.ui.CountryVMTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CountryVMTest::class
)
class WalmartTestSuite