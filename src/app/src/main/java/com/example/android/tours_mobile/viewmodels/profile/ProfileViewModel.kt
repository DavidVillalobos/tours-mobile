package com.example.android.tours_mobile.viewmodels.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.tours_mobile.repositories.user.UserRepository
import com.example.android.tours_mobile.services.dto.UserDTO

class ProfileViewModel : ViewModel(){
    var currentUser = MutableLiveData<UserDTO?>()
    private var userRepository = UserRepository()

    init {
        currentUser = userRepository.currentUser
    }

    fun authenticateUser(user : UserDTO){
        userRepository.authenticateUser(user)
    }

}