package com.aysesenses.remoteconfigexample

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aysesenses.remoteconfigexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RemoteConfigUtils.init()

        binding.button.text = RemoteConfigUtils.getNextButtonText()
        binding.button.setBackgroundColor(Color.parseColor(RemoteConfigUtils.getNextButtonColor()))
    }
}
