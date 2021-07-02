package com.example.weather.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailsBinding
import com.example.weather.ui.adapters.ForecastAdapter
import com.example.weather.ui.viewmodels.WeeklyForecastViewModel

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private var detailsBinding: FragmentDetailsBinding? = null

    private val itemAdapter = ForecastAdapter()
    private val viewModel by activityViewModels<WeeklyForecastViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        detailsBinding = binding

        val city = requireArguments().getString(ListFragment.CITY_NAME_KEY) ?: ""
        binding.city.text = city
        val lon = requireArguments().getDouble(ListFragment.CITY_LON_KEY)
        val lat = requireArguments().getDouble(ListFragment.CITY_LAT_KEY)
        viewModel.loadForecast(lat.toString(), lon.toString())

        binding.forecastListView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.weeklyForecastData.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it.days)
        })
    }

    override fun onDestroy() {
        detailsBinding = null
        super.onDestroy()
    }
}