package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openbreweryapi.adapter.BrewerAdapter
import com.example.openbreweryapi.databinding.ActivityRandomBreweryBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.realm.config.RealmConfig
import com.example.openbreweryapi.realm.config.operation.OperationBrewery
import com.example.openbreweryapi.services.helper.RetrofitHelper
import com.example.openbreweryapi.services.repository.OpenBreweryAPI
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class RandomBreweryActivity : AppCompatActivity(), View.OnClickListener, BrewerAdapter.BreweryAdapterInterface {
    private lateinit var binding: ActivityRandomBreweryBinding
    private lateinit var adapter: BrewerAdapter
    private lateinit var breweryData: ArrayList<openBreweryModelItem>
    private lateinit var realmConfig: RealmConfiguration
    private lateinit var getDbOperation: OperationBrewery
    private lateinit var coroutineContext: CoroutineContext
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBreweryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRandomizer.setOnClickListener(this)

        breweryData = arrayListOf()

        adapter = BrewerAdapter(breweryData, this,this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvBreweryData.layoutManager = layoutManager
        binding.rvBreweryData.adapter = adapter

        realmConfig = RealmConfig.getConfiguration()
        getDbOperation = OperationBrewery(realmConfig)
        coroutineContext = Job() + Dispatchers.IO

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
                showAnimation()
            }
        }
    }

    private fun showAnimation(){
        binding.rvBreweryData.visibility = View.GONE
        binding.animationLoading.visibility = View.VISIBLE

        object : CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                    binding.rvBreweryData.visibility = View.VISIBLE
                    binding.animationLoading.visibility = View.GONE
                }
        }.start()
    }

    override fun removeFav(id: String) {
        val scope = CoroutineScope(coroutineContext + CoroutineName("RemoveDBEntry"))
        scope.launch (Dispatchers.IO) {
            getDbOperation.removeBrewery(id)
        }
    }

    override fun addFav(
        id: String,
        name: String,
        type: String,
        phone: String,
        state: String,
        country: String,
        position: Int
    ) {
        val scope = CoroutineScope(coroutineContext + CoroutineName("AddToDatabase"))
        scope.launch(Dispatchers.IO) {
            getDbOperation.insertBrewery(id,name, type, phone, state, country)
        }
    }
}