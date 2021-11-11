package com.allam.act.api.json

import com.allam.act.api.json.response.CurrentWeather
import com.allam.act.api.json.response.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

interface NetworkServices {
    @GET("data/2.5/weather?q=Blitar,id&appid=4225c726885f6edf5245c653a43b7597")
    fun getCurrentWeather(): Call<CurrentWeather>

    @GET("data/2.5/weather?")
    fun getCurrentWeatherOf(@Query("q") q : String, @Query("appid") app_id: String) : Call<CurrentWeather>

//    @GET("data/2.5/onecall?lat=-8.0983&lon=112.1681&appid=4225c726885f6edf5245c653a43b7597")
//    fun getWeather(): Call<Weather>


    @GET("data/2.5/onecall?")
    fun getWeather(@Query("lat") lat:String, @Query("lon") lon:String, @Query("appid") appid: String): Call<Weather>
}

object DataServices {
    fun create(): NetworkServices {
        val retrofit = Retrofit.Builder()
            // convert json to kotlin object
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(NetworkServices::class.java)
    }
}