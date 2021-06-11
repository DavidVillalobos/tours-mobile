package com.example.android.tours_mobile.services.dto

data class TourDTO(val id: Int, val city: CityDTO, val likes: List<LikeTourDTO>?,
                   val images: List<ImageTourDTO>?, val comments: List<CommentTourDTO>?,
                   val name: String, val category: String, val description: String,
                   val date: String, val quota: Int, val reviews: Int, val duration : String,
                   val price: Float, val rating : Float, val includes: String,
                   val notIncludes: String, val liked : Boolean, val completePlace : String
)