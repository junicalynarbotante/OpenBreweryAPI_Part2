package com.example.openbreweryapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openbreweryapi.databinding.ContentViewBinding
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.services.helper.NullHelper

class PaginationBreweryListAdapter (private var breweryList: ArrayList<openBreweryModelItem>, context: Context): RecyclerView.Adapter<PaginationBreweryListAdapter.PaginatedViewHolder>(){

    inner class PaginatedViewHolder (private var binding: ContentViewBinding): RecyclerView.ViewHolder(binding.root){
       fun bind (itemData:openBreweryModelItem){
           with(binding){
               txtName.text = itemData.name
               txtTypeOfBrewery.text = itemData.brewery_type
               txtPhone.text = String.format("Phone No.: %s", NullHelper.checkNull(itemData.phone))
               txtState.text = String.format("%s, ",NullHelper.checkNull(itemData.state))
               txtCountry.text = itemData.country
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