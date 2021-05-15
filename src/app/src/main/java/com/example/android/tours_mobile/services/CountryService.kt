package com.example.android.tours_mobile.services

import com.example.android.tours_mobile.services.dto.CountryDTO
import retrofit2.Call
import retrofit2.http.*

interface CountryService {
    @GET("/api/v1/countries/{id}")
    fun getCountry(@Path("id") id : Int): Call<CountryDTO>

    @GET("/api/v1/countries/with-cities/{id}")
    fun getCountryWithCities(@Path("id") id : Int): Call<CountryDTO>

    @GET("/api/v1/with-cities/countries")
    fun getCountriesWithCities(): Call<List<CountryDTO>>

    @GET("/api/v1/countries")
    fun getCountries(): Call<List<CountryDTO>>

    @POST("/api/v1/countries")
    fun createCountry(@Body country: CountryDTO): Call<CountryDTO>

    @PUT("/api/v1/countries")
    fun updateCountry(@Body country: CountryDTO): Call<CountryDTO>

    @DELETE("/api/v1/countries/{id}")
    fun deleteCountry(@Path("id") id : Int): Call<Int>

}