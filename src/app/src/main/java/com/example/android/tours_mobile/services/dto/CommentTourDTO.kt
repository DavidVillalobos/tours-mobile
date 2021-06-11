package com.example.android.tours_mobile.services.dto

data class CommentTourDTO(
        val id: Int,
        val tour: TourDTO,
        val user: UserDTO,
        val rating: Float,
        val description : String
)
