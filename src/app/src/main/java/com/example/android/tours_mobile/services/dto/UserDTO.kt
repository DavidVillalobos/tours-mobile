package com.example.android.tours_mobile.services.dto

data class UserDTO(val id: Int, val country: CountryDTO, val email: String,
                   val password: String, val name: String, val lastName: String,
                   val identification: String, val birthday : String, val admin: Int) {
    constructor(email : String, password: String) :
            this(-1, CountryDTO(-1, "", emptyList()), email, password,
            "" , "", "", "", 0){
    }

}
