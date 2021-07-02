package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.api.DailyWeather
import com.example.weather.databinding.DailyWeatherItemBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var days = mutableListOf<DailyWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            DailyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = SimpleDateFormat("dd.MM").format(Date(days[position].date * 1000))
        holder.bind(date, days[position].tempData.day, days[position].tempData.night)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    fun addItems(newItems: List<DailyWeather>) {
        days = newItems as MutableList<DailyWeather>
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: DailyWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: String, tempDay: Double, tempNight: Double) {
            binding.day.text = day
            binding.tempDay.text = "${tempDay.roundToInt()}\u2103"
            binding.tempNight.text = "${tempNight.roundToInt()}\u2103"
        }
    }
}