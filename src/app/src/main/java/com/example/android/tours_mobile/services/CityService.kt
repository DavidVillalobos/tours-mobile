package com.example.android.tours_mobile.services

import com.example.android.tours_mobile.services.dto.CityDTO

import retrofit2.Call
import retrofit2.http.*

interface CityService {

    @GET("/api/v1/cities/{id}")
    fun getCity(@Path("id") id : Int): Call<CityDTO>

    @GET("/api/v1/cities")
    fun getCities(): Call<List<CityDTO>>

    @GET("/api/v1/cities/country/{id}")
    fun getCitiesByCountry(@Path("id") id : Int): Call<List<CityDTO>>

    @POST("/api/v1/cities")
    fun createCity(@Body city: CityDTO): Call<CityDTO>

    @PUT("/api/v1/cities")
    fun updateCity(@Body city: CityDTO): Call<CityDTO>

    @DELETE("/api/v1/cities/{id}")
    fun deleteCity(@Path("id") id : Int): Call<Int>

}