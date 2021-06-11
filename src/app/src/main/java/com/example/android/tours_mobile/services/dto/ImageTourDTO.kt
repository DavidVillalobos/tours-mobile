package com.example.android.tours_mobile.services.dto

data class ImageTourDTO(
        val id: Int,
        val tour : TourDTO,
        val photo : String,
        val photoBase64 : String,
        val mainPhoto : Int
)
