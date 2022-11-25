package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openbreweryapi.databinding.ActivityBreweryListBinding
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding

class BreweryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreweryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreweryListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        
    }


}