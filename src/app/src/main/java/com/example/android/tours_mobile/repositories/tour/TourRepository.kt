package com.example.android.tours_mobile.repositories.tour

import androidx.lifecycle.MutableLiveData
import com.example.android.tours_mobile.helpers.ServiceAdapter
import com.example.android.tours_mobile.services.dto.TourDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TourRepository {
    private var webServiceTour = ServiceAdapter.getTourService()
    var filterTours = MutableLiveData<List<TourDTO>>()
    var stateSearch = MutableLiveData<Int>()
    init {
        filterTours.value = arrayListOf()
        stateSearch.value = 0
    }

    fun filterTours(place : String?, departure : String?, arrival : String?, id_user : Int?){
        stateSearch.value = 1
        webServiceTour.getFilterTours(place, departure, arrival, id_user).enqueue(object : Callback<List<TourDTO>> {
                override fun onFailure(call: Call<List<TourDTO>>, t: Throwable) {
                    filterTours.value = arrayListOf()
                    stateSearch.value = 4
                }
                override fun onResponse(
                    call: Call<List<TourDTO>>,
                    response: Response<List<TourDTO>>
                ) {
                    if(response.isSuccessful) {
                        filterTours.value = response.body()
                        stateSearch.value = if(filterTours.value!!.isEmpty()) 3 else 2
                    } else {
                        filterTours.value = arrayListOf()
                        stateSearch.value = 3
                    }
                }
        })
    }

    // All methods from TourService...
}
