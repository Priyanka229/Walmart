package com.android.walmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.walmart.databinding.ActivityMainBinding
import com.android.walmart.ui.CountryListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** inflate view */
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        /** load fragment only if it is not already loaded */
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(binding.container.id, CountryListFragment.newInstance())
                    .commit()
        }
    }
}