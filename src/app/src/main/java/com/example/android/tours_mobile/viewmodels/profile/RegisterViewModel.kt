package com.example.android.tours_mobile.viewmodels.profile

import android.util.Patterns
import androidx.lifecycle.*
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.repositories.country.CountryRepository
import com.example.android.tours_mobile.repositories.user.UserRepository
import com.example.android.tours_mobile.services.dto.CountryDTO
import com.example.android.tours_mobile.services.dto.UserDTO

class RegisterViewModel : ViewModel(){
    private var userRepository = UserRepository()
    private var countryRepository = CountryRepository()
    var countries = MutableLiveData<List<CountryDTO>>()

    var id = MutableLiveData<String>()
    var name = MutableLiveData<String>()
    var lastname = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var country = MutableLiveData<CountryDTO>()
    var birthday = MutableLiveData<String>()
    var stateAddUser = MutableLiveData<Int>()

    init {
        countries = countryRepository.countries
        stateAddUser = userRepository.stateAddUser
    }

    val idErrorMessage =
        Transformations.map(id) { value ->
            when {
                value.isBlank() -> "The id cannot be blank"
                else -> null
            }
        }

    val nameErrorMessage =
        Transformations.map(name) { value ->
            when {
                value.isBlank() -> "The name cannot be blank"
                else -> null
            }
        }

    val lastnameErrorMessage =
        Transformations.map(lastname) { value ->
            when {
                value.isBlank() -> "The lastname cannot be blank"
                else -> null
            }
        }

    val emailErrorMessage =
        Transformations.map(email) { value ->
            when {
                value.isBlank() -> "The email cannot be blank"
                !value.matches(Patterns.EMAIL_ADDRESS.toRegex()) -> "The email must be an email"
                else -> null
            }
        }

    val passwordErrorMessage = Transformations.map(password) { value ->
        when {
            value.length < 8 -> "The password cannot have less than 8 characters"
            value.contains("""\s""".toRegex()) -> "The password cannot contain space characters"
            !value.contains("""\d""".toRegex()) -> "The password must contain at least one digit"
            !value.contains("""[a-z]""".toRegex()) -> "The password must contain at least one lowercase letter"
            !value.contains("""[A-Z]""".toRegex()) -> "The password must contain at least one uppercase letter"
            !value.contains("""\W""".toRegex()) -> "The password must contain at least one special character"
            else -> null
        }
    }

    val countryErrorMessage =
        Transformations.map(country) { value ->
            when(value.id){
                 -1 -> R.string.country_origin
                else -> null
            }
        }

    val birthdayErrorMessage =
        Transformations.map(email) { value ->
            when {
                value.isBlank() -> R.string.required_email
                !value.matches(Patterns.EMAIL_ADDRESS.toRegex()) -> "The email must be an email"
                else -> null
            }
        }

    val isValid = MediatorLiveData<Boolean>().apply {
        val onChanged = Observer<String?> {
            this.value = idErrorMessage.value == null &&
                    nameErrorMessage.value == null &&
                    lastnameErrorMessage.value == null &&
                    emailErrorMessage.value == null &&
                    passwordErrorMessage.value == null &&
                    countryErrorMessage.value == null &&
                    birthdayErrorMessage.value == null
        }
        addSource(idErrorMessage, onChanged)
        addSource(nameErrorMessage, onChanged)
        addSource(lastnameErrorMessage, onChanged)
        addSource(emailErrorMessage, onChanged)
        addSource(passwordErrorMessage, onChanged)
        addSource(countryErrorMessage, onChanged)
        addSource(birthdayErrorMessage, onChanged)
    }

    fun addUser(){
        if(isValid.value == true) {
            val user = UserDTO(
                0, country.value!!, email.value!!, password.value!!,
                name.value!!, lastname.value!!, id.value!!, birthday.value!!, 0
            );
            userRepository.createUser(user)
        }
    }

    fun getCountries(){
        countryRepository.getCountries()
    }
}