package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding

class RandomBreweryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRandomBreweryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRandomizer.setOnClickListener(this)

    }



    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btn_randomizer) -> {


            }
        }
    }
}