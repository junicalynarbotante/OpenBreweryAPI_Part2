package com.example.openbreweryapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.openbreweryapi.databinding.ContentViewBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.services.helper.NullHelper.Companion.checkNull

class BrewerAdapter ( private var breweryList: ArrayList<openBreweryModelItem>, private var context: Context, private var breweryAdapterCallback: BreweryAdapterInterface) : RecyclerView.Adapter<BrewerAdapter.BrewerViewHolder>(){
    interface BreweryAdapterInterface{
        fun removeFav (id:String)
        fun addFav (id: String,name:String, type:String,phone:String,state:String,country:String,position: Int)
    }
    inner class BrewerViewHolder (private val binding: ContentViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (itemData: openBreweryModelItem){
            val phoneData = checkNull(itemData.phone)
            val stateData = checkNull(itemData.state)
            with (binding){
                txtName.text = itemData.name
                txtTypeOfBrewery.text = itemData.brewery_type
                txtPhone.text = String.format("Phone No.: %s", checkNull(itemData.phone))
                txtState.text = String.format("%s, ", checkNull(itemData.state))
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrewerViewHolder {
        val binding = ContentViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return BrewerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrewerViewHolder, position: Int) {
        val breweryDataL = breweryList[position]
        holder.bind(breweryDataL)
    }

    override fun getItemCount(): Int {
        return breweryList.size
    }
    fun updateData(breweryDataList: ArrayList<openBreweryModelItem>){
        this.breweryList = arrayListOf()
        notifyDataSetChanged()
        this.breweryList = breweryDataList
        this.notifyItemInserted(this.breweryList.size)
    }
}