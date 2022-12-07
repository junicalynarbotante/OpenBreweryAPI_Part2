package com.example.openbreweryapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.example.openbreweryapi.databinding.ContentViewBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.realm.config.RealmConfig
import com.example.openbreweryapi.realm.config.operation.OperationBrewery
import com.example.openbreweryapi.services.helper.NullHelper
import com.example.openbreweryapi.services.helper.NullHelper.Companion.checkNull
import kotlinx.coroutines.*

class PaginationBreweryListAdapter (private var breweryList: ArrayList<openBreweryModelItem>, private var context: Context, private var breweryAdapterCallback: PaginationBreweryListAdapterInterface): RecyclerView.Adapter<PaginationBreweryListAdapter.PaginatedViewHolder>(){
    interface PaginationBreweryListAdapterInterface{
        fun removeFav(id: String)
        fun addFav (id: String,name:String, type:String,phone:String,state:String,country:String,position: Int)
    }
    inner class PaginatedViewHolder (private var binding: ContentViewBinding): RecyclerView.ViewHolder(binding.root){
       fun bind (itemData:openBreweryModelItem){
           val phoneData = checkNull(itemData.phone)
           val stateData =checkNull(itemData.state)
           with (binding){
               txtName.text = itemData.name
               txtTypeOfBrewery.text = itemData.brewery_type
               txtPhone.text = String.format("Phone No.: %s", phoneData)
               txtState.text = String.format("%s, ", stateData)
               txtCountry.text = itemData.country

               favorite.setOnClickListener{
                   val getState = binding.favorite.isChecked
                   if (getState){
                       breweryAdapterCallback.addFav(itemData.id,itemData.name,itemData.brewery_type,phoneData,stateData,itemData.country,adapterPosition)
                       Toast.makeText(context,"Added to Favorites", Toast.LENGTH_SHORT).show()
                   } else {
                       breweryAdapterCallback.removeFav(itemData.id)
                       Toast.makeText(context,"Removed from favorites", Toast.LENGTH_SHORT).show()
                   }
               }
           }
           val realmConfig = RealmConfig.getConfiguration()
           val getDbOperations = OperationBrewery(realmConfig)

           val coroutineContext = Job() + Dispatchers.IO
           val scope = CoroutineScope(coroutineContext + CoroutineName("SearchIdFromDB"))
           scope.launch (Dispatchers.IO){
               val result = getDbOperations.filterBrewery(itemData.id)
               if (result.size != 0) {
                   binding.favorite.isClickable = true
                   scope.coroutineContext.cancel()
               }
           }
       }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaginatedViewHolder {
        val binding = ContentViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PaginatedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaginatedViewHolder, position: Int) {
        val breweryDataL = breweryList[position]
        return holder.bind(breweryDataL)
    }

    override fun getItemCount(): Int {
        return breweryList.size
    }

    fun updateData(breweryDataList: ArrayList<openBreweryModelItem>){
        this.breweryList.addAll(breweryDataList)
        this.notifyItemInserted(breweryList.size)
    }

    fun resetData(){
        this.breweryList.clear()
        this.breweryList = arrayListOf()
        this.notifyDataSetChanged()
    }
}