package com.example.android.tours_mobile.viewmodels.profile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.*
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.repositories.country.CountryRepository
import com.example.android.tours_mobile.repositories.user.UserRepository
import com.example.android.tours_mobile.services.dto.CountryDTO
import com.example.android.tours_mobile.services.dto.UserDTO

class RegisterViewModel() : ViewModel(){

    @SuppressLint("StaticFieldLeak")
    lateinit var context : Context
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
                value.isBlank() -> context.resources.getString(R.string.required_identification)
                else -> null
            }
        }

    val nameErrorMessage =
        Transformations.map(name) { value ->
            when {
                value.isBlank() -> context.resources.getString(R.string.required_name)
                else -> null
            }
        }

    val lastnameErrorMessage =
        Transformations.map(lastname) { value ->
            when {
                value.isBlank() -> context.resources.getString(R.string.required_last_name)
                else -> null
            }
        }

    val emailErrorMessage =
        Transformations.map(email) { value ->
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

    val countryErrorMessage =
        Transformations.map(country) { value ->
            when(value.id){
                 -1 -> context.resources.getString(R.string.country_origin)
                else -> null
            }
        }

    val birthdayErrorMessage =
        Transformations.map(birthday) { value ->
            when {
                value.isBlank() -> context.resources.getString(R.string.required_birthday)
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