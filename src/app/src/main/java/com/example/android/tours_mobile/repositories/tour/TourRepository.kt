package com.example.android.tours_mobile.repositories.tour

import androidx.lifecycle.MutableLiveData
import com.example.android.tours_mobile.ServiceAdapter
import com.example.android.tours_mobile.services.dto.TourDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourRepository {
    var webServiceTour = ServiceAdapter.getTourService()
    var filterTours = MutableLiveData<List<TourDTO>>()

    init {
        filterTours.value = arrayListOf()
    }

    fun filterTours(place : String?, departure : String?, arrival : String?, id_user : Int?){
        webServiceTour.getFilterTours(place, departure, arrival, id_user).enqueue(object : Callback<List<TourDTO>> {
                override fun onFailure(call: Call<List<TourDTO>>, t: Throwable) {
                    filterTours.value = arrayListOf()
                }
                override fun onResponse(
                    call: Call<List<TourDTO>>,
                    response: Response<List<TourDTO>>
                ) {
                    if(response.isSuccessful) {
                        filterTours.value = response.body()
                    } else {
                        filterTours.value = arrayListOf()
                    }
                }
        })
    }

    // All methods from TourService...
}
