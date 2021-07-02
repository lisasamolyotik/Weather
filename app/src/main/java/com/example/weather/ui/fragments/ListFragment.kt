package com.example.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.App
import com.example.weather.R
import com.example.weather.databinding.FragmentListBinding
import com.example.weather.ui.adapters.CitiesAdapter
import com.example.weather.ui.viewmodels.WeatherViewModel
import com.example.weather.ui.viewmodels.WeatherViewModelFactory
import com.example.weather.util.ConnectionLiveData

class ListFragment : Fragment(R.layout.fragment_list) {
    private var listFragmentBinding: FragmentListBinding? = null
    private val itemAdapter = CitiesAdapter()

    private var first = true

    private var hasConnection = false

    private val viewModel: WeatherViewModel by activityViewModels<WeatherViewModel> {
        WeatherViewModelFactory((requireActivity().application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListBinding.bind(view)
        listFragmentBinding = binding



        binding.fab.setOnClickListener {
            if (hasConnection) {
                parentFragmentManager.commit {
                    replace<AddCityFragment>(R.id.container)
                    addToBackStack(null)
                }
            } else {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        itemAdapter.onItemClick = { city ->
            if (hasConnection) {
                parentFragmentManager.commit {
                    val detailsFragment = DetailsFragment()
                    val bundle = Bundle()
                    bundle.putString(CITY_NAME_KEY, city.name)
                    bundle.putDouble(CITY_LON_KEY, city.lon)
                    bundle.putDouble(CITY_LAT_KEY, city.lat)
                    detailsFragment.arguments = bundle
                    replace(R.id.container, detailsFragment)
                    addToBackStack(null)
                }
            } else {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.items.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
            Log.d("tag", "observer called")
            updateWeatherFromApi()
        })


    }

    private fun updateWeatherFromApi() {
        val connection = ConnectionLiveData(requireActivity().application)

        connection.observe(viewLifecycleOwner, Observer {
            hasConnection = it
            if (it && first) {
                viewModel.updateWeather()
                first = false
            }
        })
    }

    override fun onDestroy() {
        listFragmentBinding = null
        super.onDestroy()
    }

    companion object {
        const val CITY_NAME_KEY = "CITY_NAME_KEY"
        const val CITY_LON_KEY = "CITY_LON_KEY"
        const val CITY_LAT_KEY = "CITY_LAT_KEY"
    }
}