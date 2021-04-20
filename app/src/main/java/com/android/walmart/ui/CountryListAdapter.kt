package com.android.walmart.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.walmart.beans.Country
import com.android.walmart.databinding.AdapterItemCountryBinding

class CountryListAdapter : ListAdapter<Country, CountryListAdapter.CountryViewHolder>(
    DiffCallback()
) {
    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areContentsTheSame(oldItem: Country, newItem: Country) = oldItem == newItem
        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.name == newItem.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            AdapterItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountryViewHolder(private val binding: AdapterItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.apply {

                nameRegionTv.apply {
                    val countryName = "${country.name} , ${country.region}"
                    text = countryName
                    visibility = if (country.name.isBlank()) View.GONE else View.VISIBLE
                }

                codeTv.apply {
                    val countryCode = "${country.code}"
                    text = countryCode
                    visibility = if (country.code.isNullOrBlank()) View.GONE else View.VISIBLE
                }

                capitalTv.apply {
                    val countryCapital = "${country.capital}"
                    text = countryCapital
                    visibility = if (country.capital.isNullOrBlank()) View.GONE else View.VISIBLE
                }
            }
        }
    }
}