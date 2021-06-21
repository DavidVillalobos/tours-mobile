package com.example.android.tours_mobile.viewmodels.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tours_mobile.repositories.tour.TourRepository
import com.example.android.tours_mobile.services.dto.TourDTO

class ExploreViewModel : ViewModel(){

    var tours = MutableLiveData<List<TourDTO>>()
    var stateSearch = MutableLiveData<Int>()
    private var toursRepository = TourRepository()
    var departure = MutableLiveData<String>()
    var arrival = MutableLiveData<String>()
    var place = MutableLiveData<String>()
    var id_user = MutableLiveData<Int>()

    init {
        tours = toursRepository.filterTours
        stateSearch  = toursRepository.stateSearch
    }

    fun searchTours(){
        toursRepository.filterTours(place.value, departure.value, arrival.value, id_user.value)
    }

}