package com.example.android.tours_mobile

import com.example.android.tours_mobile.services.CityService
import com.example.android.tours_mobile.services.CountryService
import com.example.android.tours_mobile.services.UserService

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceAdapter{

    private const val BASE_URL: String = "http://1175cfe1e02e.ngrok.io"

    private var user_service : UserService
    private var country_service : CountryService
    private var city_service : CityService

    init {
         val retrofit : Retrofit  = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        user_service = retrofit.create(UserService::class.java)
        country_service = retrofit.create(CountryService::class.java)
        city_service = retrofit.create(CityService::class.java)
    }

    fun getUserService(): UserService {
        return user_service;
    }

    fun getCountryService(): CountryService {
        return country_service;
    }

    fun getCityService(): CityService {
        return city_service;
    }

}