package com.android.walmart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.walmart.base.BaseWalmartFragment
import com.android.walmart.databinding.FragmentCountryListBinding
import com.android.walmart.utilities.WalmartUtility

class CountryListFragment : BaseWalmartFragment() {
    lateinit var binding: FragmentCountryListBinding
    private lateinit var countryAdapter: CountryListAdapter

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private lateinit var viewModel: CountryVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryAdapter = CountryListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** set up country list view */
        binding.apply {
            countryRv.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = countryAdapter
            }
        }

        /** set up click listeners */
        setUpClickListeners()

        /** view model */
        viewModel = ViewModelProvider(activity!!).get(CountryVM::class.java)

        /** observers */
        setUpObservers()

        /** resume the view-model */
        viewModel.countryListScreenLaunched()
    }

    private fun setUpClickListeners() {
        binding.apply {
            /** action click */
            actionBtn.setOnClickListener {
                viewModel.fetchCountries()
            }
        }
    }
    private fun setUpObservers() {
        /** data observer */
        viewModel.countryLiveData.observe(viewLifecycleOwner) { handleCountryResponse(it) }

        /** loader observer */
        viewModel.uiBlockingLoaderLiveEvent.observe(viewLifecycleOwner) { showProgress ->
            binding.progress.visibility = if (showProgress) View.VISIBLE else View.GONE
        }

        /** msg observer */
        viewModel.showMsgLiveEvent.observe(viewLifecycleOwner) {
            showMsg(it)
        }
    }

    private fun handleCountryResponse(data: CountryVM.CountryData) {
        binding.apply {
            when (data) {
                is CountryVM.CountryData.Success -> {
                    msgTv.visibility = View.GONE

                    actionBtn.visibility = View.GONE

                    countryRv.visibility = View.VISIBLE
                    countryAdapter.submitList(data.data)
                }

                is CountryVM.CountryData.NoData -> {
                    msgTv.visibility = View.VISIBLE
                    msgTv.text = data.msg

                    actionBtn.visibility = View.GONE

                    countryRv.visibility = View.GONE
                }

                is CountryVM.CountryData.Retry -> {
                    msgTv.visibility = View.VISIBLE
                    msgTv.text = data.msg

                    actionBtn.visibility = View.VISIBLE
                    actionBtn.text = data.actionText

                    countryRv.visibility = View.GONE
                }
            }
        }
    }
}