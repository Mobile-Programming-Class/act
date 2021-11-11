package com.allam.act.api.json

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.allam.act.R
import com.allam.act.api.json.response.CurrentWeather
import com.allam.act.api.json.response.Weather
import com.allam.act.api.json.response.WeatherItem
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsonActivity : AppCompatActivity() {
    var dataSet: MutableList<WeatherItem> = mutableListOf()
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_json)

        initSearchFun()

        recyclerViewAdapter = RecyclerViewAdapter(this, dataSet, R.layout.item_weather_json)
        recyclerView = findViewById(R.id.rvWeather)
        recyclerView.adapter = recyclerViewAdapter

        val networkServices = DataServices.create()
//        lat=-8.0983&lon=112.1681&appid=c9253bbc87859a49fb28ec2b6d0d2e91
        val call = networkServices.getWeather((-8.0983).toString(), (112.1681).toString(), "4225c726885f6edf5245c653a43b7597")

        call.enqueue(object : Callback<Weather> {
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                println("On Failure")
                println(t.message)
                Toast.makeText(getApplicationContext(), "Failed Getting Response" + t.message,
                    Toast.LENGTH_LONG).show()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                println("On Response")
                Toast.makeText(getApplicationContext(), "Success Getting Response",
                    Toast.LENGTH_LONG).show()
                if (response.body() != null) {
                    val data: Weather = response.body()!!
                    Toast.makeText(getApplicationContext(), "Success Getting Info, timezone: " + data.timezone,
                        Toast.LENGTH_LONG).show()
                    if (data!!.hourly!!.isNotEmpty()) {
                        println(data)
                        val newData: MutableList<WeatherItem> = mutableListOf()
                        for (hourlyData in data!!.hourly!!) {
                            newData.add(hourlyData!!.weather!!.first()!!)
                        }
                        dataSet.addAll(newData)
                    }
                    println(dataSet)
                    if (dataSet != null) {
                        println("Dataset isnt null")
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                } else {
                    println("response body null")
                }
            }
        })
    }

    private fun initSearchFun () {

        //icons
        val ic_rain = Glide.with(this).load("https://allam0053.github.io/api/drawable/Rain.png")
        val ic_thunderstorm = Glide.with(this).load("https://allam0053.github.io/api/drawable/Thunderstorm.png")
        val ic_snow = Glide.with(this).load("https://allam0053.github.io/api/drawable/Snow.png")
        val ic_clear = Glide.with(this).load("https://allam0053.github.io/api/drawable/Clear.png")
        val ic_cloud = Glide.with(this).load("https://allam0053.github.io/api/drawable/Clouds.png")
        val ic_else = Glide.with(this).load("https://allam0053.github.io/api/drawable/Else.png")

        val btnCari = findViewById<Button>(R.id.btnCari)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        var ivIconHasil = findViewById<ImageView>(R.id.ivIconHasil)
        btnCari.setOnClickListener(){
            val city = etSearch.text.toString()

            val networkServices = DataServices.create()
            val call = networkServices.getCurrentWeatherOf(city, "4225c726885f6edf5245c653a43b7597")


            call.enqueue(object : Callback<CurrentWeather>{
                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                    println("On Failure")
                    println(t.message)
                    Toast.makeText(getApplicationContext(), "Failed Getting Response" + t.message,
                        Toast.LENGTH_LONG).show()
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<CurrentWeather>,response: Response<CurrentWeather>) {
                    Toast.makeText(getApplicationContext(), "Success Getting Response",
                        Toast.LENGTH_LONG).show()
                    if (response.body() != null) {
                        val data: CurrentWeather = response.body()!!
                        Toast.makeText(getApplicationContext(), "Success Getting Info, timezone: " + data.timezone,
                            Toast.LENGTH_LONG).show()
                        if (data!!.weather!!.isNotEmpty()) {
                            val tvHasil = findViewById<TextView>(R.id.tvHasil)
                            tvHasil.text = "Kota: " + city + " " + data!!.weather!!.first()!!.main

                            if (data!!.weather!!.first()!!.main.equals("Rain") || data!!.weather!!.first()!!.main.equals("Drizzle"))
                                ic_rain.into(ivIconHasil)
                            else if (data!!.weather!!.first()!!.main.equals("Thunderstorm"))
                                ic_thunderstorm.into(ivIconHasil)
                            else if (data!!.weather!!.first()!!.main.equals("Snow"))
                                ic_snow.into(ivIconHasil)
                            else if (data!!.weather!!.first()!!.main.equals("Clear"))
                                ic_clear.into(ivIconHasil)
                            else if (data!!.weather!!.first()!!.main.equals("Clouds"))
                                ic_cloud.into(ivIconHasil)
                            else
                                ic_else.into(ivIconHasil)

                        } else {
                            val tvHasil = findViewById<TextView>(R.id.tvHasil)
                            tvHasil.text = "Maaf terjadi kesalahan (data kosong)"
                        }
                    } else {
                        val tvHasil = findViewById<TextView>(R.id.tvHasil)
                        tvHasil.text = "response body null " + city
                    }
                }
            })
        }
    }
}