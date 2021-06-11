package com.example.android.tours_mobile.viewmodels.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tours_mobile.repositories.tour.TourRepository
import com.example.android.tours_mobile.services.dto.TourDTO

class ExploreViewModel : ViewModel(){

    var tours = MutableLiveData<List<TourDTO>>()
    var stateSearch = MutableLiveData<Int>()
    private var toursRepository = TourRepository()

    init {
        tours = toursRepository.filterTours
        stateSearch  = toursRepository.stateSearch
    }

    fun searchTours(place : String?, departure : String?, arrival : String?, id_user : Int?){
        toursRepository.filterTours(place, departure, arrival, id_user)
    }

}