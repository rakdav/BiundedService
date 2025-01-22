package com.example.biundedservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.biundedservice.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var binded = false
    private var weatherService: WeatherService? = null
    private val weatherServiceConnection by lazy {
        object: ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
                val binder:WeatherService.LocalWeatherbinder=
                   p1 as WeatherService.LocalWeatherbinder
                weatherService=binder.getService()
                binded=true
            }
            override fun onServiceDisconnected(p0: ComponentName?) {
                binded=false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val location=binding.editTextText.text.toString()
            val weather= weatherService?.getWeatherToday(location)
            binding.textView2.text=weather
        }
    }

    override fun onStart() {
        super.onStart()
        val intent=Intent(this,WeatherService::class.java)
        this.bindService(intent,weatherServiceConnection,Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if(binded){
            this.unbindService(weatherServiceConnection)
            binded=false
        }
    }
}