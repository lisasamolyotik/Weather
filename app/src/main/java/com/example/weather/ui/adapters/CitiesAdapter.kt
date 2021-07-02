package com.example.weather.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.ViewItemBinding
import com.example.weather.db.CityWeather
import kotlin.math.roundToInt

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private var cities = mutableListOf<CityWeather>()
    var onItemClick: ((CityWeather) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position].name, cities[position].temp)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun addItems(newItems: List<CityWeather>) {
        cities = newItems as MutableList<CityWeather>
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(cities[bindingAdapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(city: String, temp: Double) {
            binding.cityItem.text = city
            binding.tempItem.text = "${temp.roundToInt()}\u2103"
        }
    }
}