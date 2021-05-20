package com.example.android.tours_mobile.fragments.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.ServiceAdapter
import com.example.android.tours_mobile.databinding.FragmentRegisterBinding
import com.example.android.tours_mobile.services.dto.CountryDTO
import com.example.android.tours_mobile.services.dto.UserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RegisterFragment : Fragment() {

    private var datePickerDialog: DatePickerDialog? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val arrayAdapter : ArrayAdapter<CountryDTO> = ArrayAdapter<CountryDTO>(requireContext(), android.R.layout.simple_spinner_item, mutableListOf())
        arrayAdapter.add(CountryDTO(-1, getString(com.example.android.tours_mobile.R.string.country_origin), emptyList()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.adapter = arrayAdapter
        binding.spinnerCountry.prompt = getString(com.example.android.tours_mobile.R.string.label_country)
        binding.buttonSignUp.isEnabled = false
        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                validateFields()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        binding.editTextIdentification.addTextChangedListener { validateFields() }
        binding.editTextName.addTextChangedListener { validateFields() }
        binding.editTextLastName.addTextChangedListener { validateFields() }
        binding.editTextBirthday.addTextChangedListener { validateFields() }
        binding.editTextEmailRegister.addTextChangedListener { validateFields() }
        binding.editTextPasswordRegister.addTextChangedListener { validateFields() }

        ServiceAdapter.getCountryService().getCountries().enqueue(object : Callback<List<CountryDTO>> {
            override fun onFailure(call: Call<List<CountryDTO>>, t: Throwable) {
                Log.d("TAG_", "An error happened!")
            }
            override fun onResponse(
                call: Call<List<CountryDTO>>,
                response: Response<List<CountryDTO>>
            ) {
                Log.d("TAG_", response.body().toString())
                if(response.isSuccessful) arrayAdapter.addAll(response.body().orEmpty())
            }
        })

        binding.buttonHidePasswordRegister.isChecked = false
        binding.buttonHidePasswordRegister.setOnClickListener{
            binding.editTextPasswordRegister.transformationMethod =
                if(binding.buttonHidePasswordRegister.isChecked) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
        }
        binding.editTextBirthday.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        val month = monthOfYear + 1
                        var fm = "" + month
                        var fd = "" + dayOfMonth
                        if (month < 10) {
                            fm = "0$month"
                        }
                        if (dayOfMonth < 10) {
                            fd = "0$dayOfMonth"
                        }
                        binding.editTextBirthday.setText("$year-$fm-$fd")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog!!.datePicker.maxDate = Date().time
            datePickerDialog!!.show()
        }

        binding.buttonSignUp.setOnClickListener{
            val countrySelected = binding.spinnerCountry.selectedItem as CountryDTO;
            val user = UserDTO(
                0, countrySelected, binding.editTextEmailRegister.text.toString(),
                binding.editTextPasswordRegister.text.toString(), binding.editTextName.text.toString(),
                binding.editTextLastName.text.toString(), binding.editTextIdentification.text.toString(),
                binding.editTextBirthday.text.toString(), 0);
            ServiceAdapter.getUserService().createUser(user).enqueue(object : Callback<Int> {
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d("TAG_", "An error happened!")
                    val toast = Toast.makeText(requireContext(), getString(R.string.user_register_failed), Toast.LENGTH_LONG)
                    toast.show()
                }
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    Log.d("TAG_", response.body().toString())
                    if(response.isSuccessful) {
                        val toast = Toast.makeText(requireContext(), getString(R.string.user_register_success), Toast.LENGTH_LONG)
                        toast.show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_register_to_profile)
                    }else{
                        val toast = Toast.makeText(requireContext(), getString(R.string.user_register_failed), Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            })
        }
        return binding.root
    }

    private fun validateFields() {
        var isValid : Boolean = true;
        val selectItem = binding.spinnerCountry.selectedItem as CountryDTO;
        if(selectItem.id == -1){
            val errorText: TextView = binding.spinnerCountry.selectedView as TextView
            errorText.setTextColor(Color.RED)
            errorText.text = getString(R.string.country_origin)
            isValid = false
        }
        if (binding.editTextIdentification.text.isEmpty()) {
            binding.editTextIdentification.error = getString(R.string.required_identification)
            isValid = false
        }
        if (binding.editTextName.text.isEmpty()) {
            binding.editTextName.error = getString(R.string.required_name)
            isValid = false
        }
        if (binding.editTextLastName.text.isEmpty()) {
            binding.editTextLastName.error = getString(R.string.required_last_name)
            isValid = false
        }
        if (binding.editTextBirthday.text.isEmpty()) {
            binding.editTextBirthday.error = getString(R.string.required_birthday)
            isValid = false
        }else{
            binding.editTextBirthday.error = null
        }
        if (binding.editTextEmailRegister.text.isEmpty()) {
            binding.editTextEmailRegister.error = getString(R.string.required_email)
            isValid = false
        }else{
            val email = binding.editTextEmailRegister.text.toString().trim()
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editTextEmailRegister.error = getString(R.string.required_format_email)
                isValid = false
            }
        }
        if (binding.editTextPasswordRegister.text.isEmpty()) {
            binding.editTextPasswordRegister.error = getString(R.string.required_password)
            isValid = false
        }else {
            val password = binding.editTextPasswordRegister.text.toString().trim()
            if (password.length < 8) {
                binding.editTextPasswordRegister.error = getString(R.string.required_password_extension)
                isValid = false
            } else if(!password.matches(Regex(".*\\d.*"))) {
                binding.editTextPasswordRegister.error = getString(R.string.required_password_numbers)
                isValid = false
            } else if (!password.matches(Regex(".*[a-z].*"))) {
                binding.editTextPasswordRegister.error = getString(R.string.required_password_lower)
                isValid = false
            } else if (!password.matches(Regex(".*[A-Z].*"))) {
                binding.editTextPasswordRegister.error = getString(R.string.required_password_upper)
                isValid = false
            } else if (!password.matches(Regex(".*\\W.*"))) {
                binding.editTextPasswordRegister.error = getString(R.string.required_password_especial_character)
                isValid = false
            }
        }
        binding.buttonSignUp.isEnabled = isValid
    }
}