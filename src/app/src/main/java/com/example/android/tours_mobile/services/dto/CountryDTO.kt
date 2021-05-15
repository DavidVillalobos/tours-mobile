package com.example.android.tours_mobile.services.dto

data class CountryDTO(
    val id: Int,
    val name: String,
    val cities : List<CityDTO>
) {
    override fun toString(): String = name
}
