package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.openbreweryapi.databinding.ActivityBreweryListBinding
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding

class BreweryListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBreweryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreweryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}