package com.example.android.tours_mobile.repositories.country

import androidx.lifecycle.MutableLiveData
import com.example.android.tours_mobile.ServiceAdapter
import com.example.android.tours_mobile.services.dto.CountryDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryRepository {
    var webServiceCountry = ServiceAdapter.getCountryService()
    var countries = MutableLiveData<List<CountryDTO>>()

    init {
        countries.value = mutableListOf()
    }

    fun getCountries(){
        webServiceCountry.getCountries().enqueue(object : Callback<List<CountryDTO>> {
                override fun onFailure(call: Call<List<CountryDTO>>, t: Throwable) {
                    countries.value = mutableListOf()
                }
                override fun onResponse(
                    call: Call<List<CountryDTO>>,
                    response: Response<List<CountryDTO>>
                ) {
                    if(response.isSuccessful) {
                        countries.value = response.body()
                    } else {
                        countries.value = mutableListOf()
                    }
                }
        })
    }

    // All methods from CountryService...
}
