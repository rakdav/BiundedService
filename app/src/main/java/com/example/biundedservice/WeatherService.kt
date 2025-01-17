package com.example.biundedservice

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.view.MenuHost
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class WeatherService : Service() {

    private val binder:IBinder=LocalWeatherbinder()

    public class LocalWeatherbinder: Binder() {
        public fun getService():WeatherService{
            return WeatherService()
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    @SuppressLint("SimpleDateFormat")
    public fun getWeatherToday(location:String): String {
        val now = Date()
        val df: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val dayString = df.format(now)
        val keyLocAndDay = "$location$$dayString"
        var weather = weatherData[keyLocAndDay]
        if (weather != null) return weather
        val weathers = arrayOf("Rainy", "Hot", "Cool", "Warm", "Snowy")
        val i: Int = Random().nextInt(5)
        weather = weathers[i]
        weatherData[keyLocAndDay] = weather
        return weather
    }
    companion object{
        val weatherData:MutableMap<String,String> = mutableMapOf()
    }

}
