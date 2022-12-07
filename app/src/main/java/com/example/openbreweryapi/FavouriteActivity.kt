package com.example.openbreweryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openbreweryapi.adapter.FavoriteBreweryAdapter
import com.example.openbreweryapi.databinding.ActivityFavouriteBinding
import com.example.openbreweryapi.models.listBreweryAPI.BreweryModelItem
import com.example.openbreweryapi.realm.config.RealmConfig
import com.example.openbreweryapi.realm.config.operation.OperationBrewery
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FavouriteActivity : AppCompatActivity(), FavoriteBreweryAdapter.FavoriteBreweryAdapterInterface {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var breweryData: ArrayList<BreweryModelItem>
    private lateinit var adapter: FavoriteBreweryAdapter
    private lateinit var realmConfig: RealmConfiguration
    private lateinit var getDbOperation: OperationBrewery
    private lateinit var coroutineContext: CoroutineContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        breweryData = arrayListOf()
        adapter = FavoriteBreweryAdapter(breweryData,this,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvBreweryFavorite.adapter = adapter
        binding.rvBreweryFavorite.layoutManager = layoutManager

        realmConfig = RealmConfig.getConfiguration()
        getDbOperation = OperationBrewery(realmConfig)
        coroutineContext = Job() + Dispatchers.IO
    }

    override fun onResume() {
        super.onResume()
        getFavoriteList()
    }

    private fun getFavoriteList(){
        val scope = CoroutineScope(coroutineContext+ CoroutineName("FetchDataFromDB"))
        scope.launch(Dispatchers.IO){
            withContext(Dispatchers.Main){
                val result = getDbOperation.retrieveBrewery()
                withContext(Dispatchers.Main){
                    adapter.updateData(result)
                }
            }
        }
    }

    override fun removeFav(id: String) {
        val scope = CoroutineScope(coroutineContext + CoroutineName("RemoveDBEntry"))
        scope.launch (Dispatchers.IO) {
            getDbOperation.removeBrewery(id)
            withContext(Dispatchers.Main){
                getFavoriteList()
            }
        }
    }
}