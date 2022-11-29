package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openbreweryapi.adapter.PaginationBreweryListAdapter
import com.example.openbreweryapi.databinding.ActivityBreweryListBinding
import com.example.openbreweryapi.databinding.ActivityMainMenuBinding
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModel
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.services.helper.RetrofitHelper
import com.example.openbreweryapi.services.repository.OpenBreweryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class BreweryListActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBreweryListBinding
    private lateinit var breweryData: ArrayList<openBreweryModelItem>
    private lateinit var adapter: PaginationBreweryListAdapter
    private var filter:String = ""
    private var page = 0
    private var isLoading:Boolean = false
    private var pageCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreweryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        breweryData = arrayListOf()

        binding.btnRefresh.setOnClickListener(this)
        binding.rbBrewpub.setOnClickListener(this)
        binding.rbMicro.setOnClickListener(this)
        binding.rbNano.setOnClickListener(this)
        binding.rbRegional.setOnClickListener(this)

        adapter = PaginationBreweryListAdapter(breweryData, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvBreweryListPaginated.layoutManager = layoutManager
        binding.rvBreweryListPaginated.adapter = adapter

        binding.llForLoading.visibility = View.GONE
        binding.rvBreweryListPaginated.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(!isLoading){
                        isLoading = true
                        whileLoading()
                        binding.llForLoading.visibility = View.VISIBLE
                        getData()
                    }
                }

            }
        })
    }
    private fun  getData(){
        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                binding.animationLoading.visibility = View.GONE
                getBrewery(filter)
            }
        }.start()
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btn_refresh) -> {
                resetData()
                getBrewery(filter)
                showLoading()
            }
            (R.id.rb_brewpub) -> {
               resetData()
                filter = "brewpub"}
            (R.id.rb_micro) -> {
               resetData()
                filter = "micro"}
            (R.id.rb_nano) -> {
               resetData()
                filter = "nano"}
            (R.id.rb_regional) -> {
                resetData()
                filter = "regional"}

        }
    }
    private fun resetData(){
        GlobalScope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                breweryData.clear()
                adapter.resetData()
            }
        }
    }

    private fun getBrewery(filter:String){
        //getBreweryData
        val breweryAPI = RetrofitHelper.getInstance().create(OpenBreweryAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val result = breweryAPI.getBreweryData(filter,5, page)
            val breweryDataResult = result.body()

            if (breweryDataResult != null) {
                breweryData.addAll(breweryDataResult)

                withContext(Dispatchers.Main) {
                    adapter.updateData(breweryDataResult)
                }
            }
        }
    }
    private fun whileLoading(){
        object : CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                isLoading = false
                pageCounter++
                getBrewery(filter)
                binding.llForLoading.visibility = View.GONE
            }
        }.start()
    }

    private fun showLoading(){
        binding.animationLoading.visibility = View.VISIBLE
        binding.animationLoading.playAnimation()
        binding.rvBreweryListPaginated.visibility = View.GONE
        object : CountDownTimer(3000,1000) {
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                binding.animationLoading.visibility = View.GONE
                binding.rvBreweryListPaginated.visibility = View.VISIBLE
                binding.animationLoading.cancelAnimation()
            }
        }.start()
    }
}