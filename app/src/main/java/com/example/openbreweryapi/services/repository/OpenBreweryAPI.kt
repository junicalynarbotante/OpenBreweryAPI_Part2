package com.example.openbreweryapi.services.repository


import com.example.openbreweryapi.models.listBreweryAPI.openBreweryModel
import com.example.openbreweryapi.models.searchBreweryAPI.searchBreweryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface OpenBreweryAPI {
    @GET("/breweries")
    suspend fun getBreweryData(
        @Query("by_type") byType:String,
        @Query("per_page") perPage:Int
    ):Response<openBreweryModel>

    @GET("/breweries/random")
    suspend fun getRandomBreweryData(
        @Query("size") size:Int
    ):Response<openBreweryModel>

    @GET("/breweries")
    suspend fun getSearchBreweryData(
        @Query("query") query: String,
    ):Response<searchBreweryModel>


}

