package com.example.openbreweryapi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openbreweryapi.adapter.BrewerAdapter
import com.example.openbreweryapi.constants.BREWERY_NAME
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivitySearchBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.models.searchBreweryAPI.searchBreweryModel
import com.example.openbreweryapi.services.helper.RetrofitHelper
import com.example.openbreweryapi.services.repository.OpenBreweryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: BrewerAdapter
    private lateinit var searchData: ArrayList<searchBreweryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(BREWERY_NAME, Context.MODE_PRIVATE)

        binding.btnSearch.setOnClickListener(this)

//        searchData = arrayListOf()
//
//        adapter = BrewerAdapter(searchData, this)
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.rvBreweryData.layoutManager = layoutManager
//        binding.rvBreweryData.adapter = adapter
//
//    }
//
//    private fun getSearchBrewery() {
//        val searchBreweryAPI = RetrofitHelper.getInstance().create(OpenBreweryAPI::class.java)
//
//        GlobalScope.launch(Dispatchers.IO) {
//            val result = searchBreweryAPI.getSearchBreweryData("Name")
//            val searchBreweryResult = result.body()
//
//            if (searchBreweryResult != null) {
//
//                withContext(Dispatchers.Main) {
//
//                }
//            }
//        }
//    }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            (R.id.btn_search) -> {
              //  getSearchBrewery()
                val breweryName = binding.txtName.text.toString()
                if (breweryName.isEmpty()) {
                    binding.txtName.error = "Required"
                    return
                }
                val editor = sharedPreferences.edit()

            }
        }
    }


}