package com.example.openbreweryapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isGone
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding

class MainMenu : AppCompatActivity() , View.OnClickListener {
    private lateinit var binding : ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBreweryList.setOnClickListener(this)
        binding.btnRandomBrewery.setOnClickListener(this)
        binding.btnFavourite.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            (R.id.btn_brewery_list) -> {
                val intent = Intent(this@MainMenu,BreweryListActivity::class.java)
                startActivity(intent)
            }
            (R.id.btn_random_brewery)-> {
                val intent = Intent(this@MainMenu,RandomBreweryActivity::class.java)
                startActivity(intent)
            }
            (R.id.btn_favourite)-> {
                val intent = Intent(this@MainMenu,FavouriteActivity::class.java)
                startActivity(intent)
            }
        }
    }
}