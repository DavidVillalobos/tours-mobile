package com.example.android.tours_mobile.viewmodels.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tours_mobile.repositories.country.CountryRepository
import com.example.android.tours_mobile.repositories.user.UserRepository
import com.example.android.tours_mobile.services.dto.CountryDTO
import com.example.android.tours_mobile.services.dto.UserDTO

class RegisterViewModel : ViewModel(){
    private var userRepository = UserRepository()
    private var countryRepository = CountryRepository()
    var countries = MutableLiveData<List<CountryDTO>>()
    var stateAddUser = MutableLiveData<Int>()

    init {
        countries = countryRepository.countries
        stateAddUser = userRepository.stateAddUser
    }

    fun addUser(user : UserDTO){
        userRepository.createUser(user)
    }

    fun getCountries(){
        countryRepository.getCountries()
    }
}