package com.example.android.tours_mobile.repositories.user

import androidx.lifecycle.MutableLiveData
import com.example.android.tours_mobile.ServiceAdapter
import com.example.android.tours_mobile.services.dto.UserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    var webServiceUser = ServiceAdapter.getUserService()
    var currentUser = MutableLiveData<UserDTO?>()
    var stateAddUser = MutableLiveData<Int>()

    init {
        currentUser.value = null
        stateAddUser.value = 0
    }

    fun authenticateUser(user : UserDTO){
        webServiceUser.authenticateUser(user).enqueue(object : Callback<UserDTO> {
                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    currentUser.value = null
                }
                override fun onResponse(
                    call: Call<UserDTO>,
                    response: Response<UserDTO>
                ) {
                    if(response.isSuccessful) {
                        currentUser.value = response.body()
                    } else {
                        currentUser.value = UserDTO("","")
                    }
                }
        })
    }

    fun createUser(user: UserDTO) {
        stateAddUser.value = 0
        webServiceUser.createUser(user).enqueue(object : Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                stateAddUser.value = -1
            }
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful) {
                    stateAddUser.value = response.body()
                } else {
                    stateAddUser.value = -1
                }
            }
        })
    }

    // All methods from UserService...
}
