package com.example.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.weather.R
import com.example.weather.databinding.FragmentAddCityBinding
import com.example.weather.ui.viewmodels.WeatherViewModel

class AddCityFragment : Fragment(R.layout.fragment_add_city) {
    private var addCityBinding: FragmentAddCityBinding? = null

    private val viewModel by activityViewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddCityBinding.bind(view)
        addCityBinding = binding

        binding.addButton.setOnClickListener {
            val city = binding.editText.text.toString()
            Log.d("tag", "city: $city")
            viewModel.addCity(city)
        }

        viewModel.respond.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            if (it) {
                parentFragmentManager.popBackStack()
            } else if (binding.editText.text!!.isNotBlank()) {
                Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
                binding.editText.setText("")
            }
        })
    }

    override fun onDestroy() {
        //viewModel.respond.removeObservers(viewLifecycleOwner)
        viewModel.respond.value = false
        addCityBinding = null
        super.onDestroy()
    }
}