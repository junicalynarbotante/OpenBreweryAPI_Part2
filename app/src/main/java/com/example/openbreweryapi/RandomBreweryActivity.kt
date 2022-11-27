package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openbreweryapi.adapter.BrewerAdapter
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.services.helper.RetrofitHelper
import com.example.openbreweryapi.services.repository.OpenBreweryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class RandomBreweryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRandomBreweryBinding
    private lateinit var adapter: BrewerAdapter
    private lateinit var breweryData: ArrayList<openBreweryModelItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRandomizer.setOnClickListener(this)

        breweryData = arrayListOf()

        adapter = BrewerAdapter(breweryData, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvBreweryData.layoutManager = layoutManager
        binding.rvBreweryData.adapter = adapter

    }

    private fun getRandomBrewery() {
        val breweryAPI = RetrofitHelper.getInstance().create(OpenBreweryAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val result = breweryAPI.getRandomBreweryData(Random.nextInt(9, 18))
            val breweryDataResult = result.body()

            if (breweryDataResult != null) {
                breweryData.clear()
                breweryData.addAll(breweryDataResult)

                withContext(Dispatchers.Main) {
                    adapter.updateData(breweryDataResult)
                }
            }
        }

    }
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            (R.id.btn_randomizer) -> {
                getRandomBrewery()
            }
        }
    }
}