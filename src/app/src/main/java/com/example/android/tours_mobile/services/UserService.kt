package com.example.android.tours_mobile.services

import com.example.android.tours_mobile.services.dto.UserDTO
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("/api/v1/users/{id}")
    fun getUser(@Path("id") id : Int): Call<UserDTO>

    @GET("/api/v1/users")
    fun getUsers(): Call<List<UserDTO>>

    @POST("/api/v1/users/authenticate")
    fun authenticateUser(@Body user: UserDTO): Call<UserDTO>

    @POST("/api/v1/users")
    fun createUser(@Body user: UserDTO): Call<Int>

    @PUT("/api/v1/users")
    fun updateUser(@Body user: UserDTO): Call<UserDTO>

    @DELETE("/api/v1/users/{id}")
    fun deleteUser(@Path("id") id : Int): Call<Int>

}