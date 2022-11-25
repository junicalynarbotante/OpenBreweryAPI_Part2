package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding

class RandomBreweryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRandomBreweryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}