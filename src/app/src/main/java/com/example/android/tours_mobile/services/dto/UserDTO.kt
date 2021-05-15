package com.example.android.tours_mobile.services.dto

// import java.util.Date

data class UserDTO(val id: Int, val country: CountryDTO, val email: String,
                   val password: String, val name: String, val lastName: String,
                   val identification: String, val birthday : String, val admin: Int)
