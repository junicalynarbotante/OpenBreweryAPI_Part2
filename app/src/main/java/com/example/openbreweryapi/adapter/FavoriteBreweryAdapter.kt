package com.example.openbreweryapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.openbreweryapi.databinding.ContentViewBinding
import com.example.openbreweryapi.models.listBreweryAPI.BreweryModelItem
import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import com.example.openbreweryapi.services.helper.NullHelper
import java.util.Collections.addAll

class FavoriteBreweryAdapter(private var favoriteList: ArrayList<BreweryModelItem>, private var context: Context, private var favoriteAdapterCallback:FavoriteBreweryAdapterInterface):RecyclerView.Adapter<FavoriteBreweryAdapter.FavoriteViewHolder>() {
    interface FavoriteBreweryAdapterInterface{
        fun removeFav(id:String)
    }
    inner class FavoriteViewHolder (private val binding: ContentViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind (itemData: BreweryModelItem){

            with (binding){
                txtName.text = itemData.name
                txtTypeOfBrewery.text = itemData.type
                txtPhone.text = String.format("Phone No.: %s", itemData.phone)
                txtState.text = String.format("%s, ", itemData.state)
                txtCountry.text = itemData.country
                favorite.isChecked =true

                favorite.setOnClickListener{
                    val getState = binding.favorite.isChecked
                    if (getState){

                    } else {
                        favoriteAdapterCallback.removeFav(itemData.id)
                        Toast.makeText(context,"Removed from favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ContentViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorData = favoriteList[position]
        return holder.bind(favorData)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
    fun updateData(favoriteList: ArrayList<BreweryModelItem>){
        this.favoriteList = arrayListOf()
        notifyDataSetChanged()
        this.favoriteList.addAll(favoriteList)
        this.notifyItemInserted(this.favoriteList.size)
    }
}