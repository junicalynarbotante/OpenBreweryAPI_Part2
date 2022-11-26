package com.example.openbreweryapi.services.repository

import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModelItem
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenBreweryAPI {
    @GET("/breweries?")
    suspend fun getBreweryList(
        @Query("by_type") brewery_type: String)
       // @Query("page") page :  Int,
       // @Query("per_page") per_page : Int,
         :   Response<openBreweryModelItem>


}

