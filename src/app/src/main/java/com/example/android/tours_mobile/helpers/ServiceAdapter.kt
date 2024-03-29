package com.example.android.tours_mobile.helpers

import com.example.android.tours_mobile.helpers.Constants.BASE_URL
import com.example.android.tours_mobile.services.CountryService
import com.example.android.tours_mobile.services.TourService
import com.example.android.tours_mobile.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceAdapter{

    private var user_service : UserService
    private var country_service : CountryService
    private var tour_service : TourService

    init {
         val retrofit : Retrofit  = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        user_service = retrofit.create(UserService::class.java)
        country_service = retrofit.create(CountryService::class.java)
        tour_service = retrofit.create(TourService::class.java)
    }

    fun getUserService(): UserService {
        return user_service;
    }

    fun getCountryService(): CountryService {
        return country_service;
    }

    fun getTourService(): TourService {
        return tour_service;
    }
}