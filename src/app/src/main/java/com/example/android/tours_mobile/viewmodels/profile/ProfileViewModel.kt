package com.example.android.tours_mobile.viewmodels.profile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.*
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.repositories.user.UserRepository
import com.example.android.tours_mobile.services.dto.UserDTO

class ProfileViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context : Context
    var currentUser = MutableLiveData<UserDTO?>()
    private var userRepository = UserRepository()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    init {
        currentUser = userRepository.currentUser
    }

    val emailErrorMessage = Transformations.map(email) { value ->
        when {
            value.isBlank() -> context.resources.getString(R.string.required_email)
            !value.matches(Patterns.EMAIL_ADDRESS.toRegex()) -> context.resources.getString(R.string.required_format_email)
            else -> null
        }
    }

    val passwordErrorMessage = Transformations.map(password) { value ->
        when {
            value.length < 8 -> context.resources.getString(R.string.required_password_extension)
            value.contains("""\s""".toRegex()) -> context.resources.getString(R.string.required_password_extension)
            !value.contains("""\d""".toRegex()) -> context.resources.getString(R.string.required_password_numbers)
            !value.contains("""[a-z]""".toRegex()) -> context.resources.getString(R.string.required_password_lower)
            !value.contains("""[A-Z]""".toRegex()) -> context.resources.getString(R.string.required_password_upper)
            !value.contains("""\W""".toRegex()) -> context.resources.getString(R.string.required_password_especial_character)
            else -> null
        }
    }

    val isValid = MediatorLiveData<Boolean>().apply {
        val onChanged = Observer<String?> {
            this.value = emailErrorMessage.value == null && passwordErrorMessage.value == null
        }
        addSource(emailErrorMessage, onChanged)
        addSource(passwordErrorMessage, onChanged)
    }

    fun authenticateUser(){
        userRepository.authenticateUser(UserDTO(
            email.value!!,
            password.value!!
        ));
    }

}

