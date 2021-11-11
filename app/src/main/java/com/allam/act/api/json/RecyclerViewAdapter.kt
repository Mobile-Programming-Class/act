package com.allam.act.api.json

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allam.act.R
import com.allam.act.api.json.response.WeatherItem
import com.bumptech.glide.Glide

class RecyclerViewAdapter (
    private val context: Context,
    private val dataSet: MutableList<WeatherItem>,
    private val rowLayout: Int
):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //icons
    val ic_rain = Glide.with(context).load("https://allam0053.github.io/api/drawable/Rain.png")
    val ic_thunderstorm = Glide.with(context).load("https://allam0053.github.io/api/drawable/Thunderstorm.png")
    val ic_snow = Glide.with(context).load("https://allam0053.github.io/api/drawable/Snow.png")
    val ic_clear = Glide.with(context).load("https://allam0053.github.io/api/drawable/Clear.png")
    val ic_cloud = Glide.with(context).load("https://allam0053.github.io/api/drawable/Clouds.png")
    val ic_else = Glide.with(context).load("https://allam0053.github.io/api/drawable/Else.png")


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weather: TextView
        val description: TextView
        val ivIcon: ImageView

        init {
            weather = view.findViewById(R.id.tvWeather)
            description = view.findViewById(R.id.tvDescription)
            ivIcon = view.findViewById(R.id.ivIcon)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(rowLayout, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.weather.text = dataSet[position].main
        if (position > 0)
            viewHolder.description.text = dataSet[position].description + ", " + position.toString() + " jam yang lalu"
        else viewHolder.description.text = dataSet[position].description + " (sekarang)"

        if (dataSet[position].main.equals("Rain") || dataSet[position].main.equals("Drizzle"))
            ic_rain.into(viewHolder.ivIcon)
        else if (dataSet[position].main.equals("Thunderstorm"))
            ic_thunderstorm.into(viewHolder.ivIcon)
        else if (dataSet[position].main.equals("Snow"))
            ic_snow.into(viewHolder.ivIcon)
        else if (dataSet[position].main.equals("Clear"))
            ic_clear.into(viewHolder.ivIcon)
        else if (dataSet[position].main.equals("Clouds"))
            ic_cloud.into(viewHolder.ivIcon)
        else
            ic_else.into(viewHolder.ivIcon)
    }

    override fun getItemCount() = dataSet.size

}