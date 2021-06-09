package com.example.android.tours_mobile.services

import com.example.android.tours_mobile.services.dto.TourDTO
import retrofit2.Call
import retrofit2.http.*

interface TourService {
    @GET("/api/v1/tours/{id}")
    fun getTour(@Path("id") id : Int): Call<TourDTO>

    @GET("/api/v1/tours")
    fun getTours(): Call<List<TourDTO>>

    @GET("/api/v1/tours/filter")
    fun getFilterTours(@Query("place") place : String?,
                       @Query("departure") departure : String?,
                       @Query("arrival") arrival : String?,
                       @Query("id_user") id_user : Int?): Call<List<TourDTO>>

    @POST("/api/v1/tours")
    fun createTour(@Body Tour: TourDTO): Call<Int>

    @PUT("/api/v1/tours")
    fun updateTour(@Body Tour: TourDTO): Call<Int>

    @DELETE("/api/v1/tours/{id}")
    fun deleteTour(@Path("id") id : Int): Call<Int>

}