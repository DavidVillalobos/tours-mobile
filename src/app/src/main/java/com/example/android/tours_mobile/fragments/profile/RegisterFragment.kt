package com.example.android.tours_mobile.fragments.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.databinding.FragmentRegisterBinding
import com.example.android.tours_mobile.services.dto.CountryDTO
import com.example.android.tours_mobile.viewmodels.profile.RegisterViewModel
import java.util.*


class RegisterFragment : Fragment() {

    private var datePickerDialog: DatePickerDialog? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        registerViewModel.context = requireContext()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val arrayAdapter : ArrayAdapter<CountryDTO> = ArrayAdapter<CountryDTO>(requireContext(), android.R.layout.simple_spinner_item, mutableListOf())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCountry.prompt = getString(R.string.label_country)
        registerViewModel.getCountries()
        registerViewModel.countries.observe(viewLifecycleOwner, {
            arrayAdapter.clear()
            arrayAdapter.add(CountryDTO(-1, getString(R.string.country_origin), emptyList()))
            arrayAdapter.addAll(it)
            binding.spinnerCountry.adapter = arrayAdapter
        })

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectItem = binding.spinnerCountry.selectedItem as CountryDTO
                registerViewModel.country.value = selectItem
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
        registerViewModel.countryErrorMessage.observe(viewLifecycleOwner, {
            if(it  != null) {
                val errorText: android.widget.TextView =
                    binding.spinnerCountry.selectedView as android.widget.TextView
                errorText.setTextColor(android.graphics.Color.RED)
                errorText.text = it
            }
        })

        registerViewModel.isValid.observe(viewLifecycleOwner, {
            binding.buttonSignUp.isEnabled = it
        })

        binding.editTextIdentification.addTextChangedListener {
            registerViewModel.id.value = it.toString()
        }
        registerViewModel.idErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextIdentification.error = it
        })

        binding.editTextName.addTextChangedListener {
            registerViewModel.name.value = it.toString()
        }
        registerViewModel.nameErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextName.error = it
        })

        binding.editTextLastName.addTextChangedListener {
            registerViewModel.lastname.value = it.toString()
        }
        registerViewModel.lastnameErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextLastName.error = it
        })

        binding.editTextEmailRegister.addTextChangedListener {
            registerViewModel.email.value = it.toString()
        }
        registerViewModel.emailErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextEmailRegister.error = it
        })

        binding.editTextPasswordRegister.addTextChangedListener {
            registerViewModel.password.value = it.toString()
        }
        registerViewModel.passwordErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextPasswordRegister.error = it
        })

        binding.editTextBirthday.addTextChangedListener {
            registerViewModel.birthday.value = it.toString()
        }
        registerViewModel.birthdayErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextBirthday.error = it
        })

        binding.editTextIdentification.setText("")
        binding.editTextName.setText("")
        binding.editTextLastName.setText("")
        binding.editTextEmailRegister.setText("")
        binding.editTextPasswordRegister.setText("")
        binding.editTextBirthday.setText("")


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
                        binding.editTextBirthday.setText("${year}-${fm}-${fd}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog!!.datePicker.maxDate = Date().time
            datePickerDialog!!.show()
        }
        binding.buttonSignUp.setOnClickListener{
            registerViewModel.addUser()
        }

        registerViewModel.stateAddUser.observe(viewLifecycleOwner, {
            if(registerViewModel.stateAddUser.value == -1) {
                Log.d("TAG_", "An error happened!")
                val toast = Toast.makeText(requireContext(), getString(R.string.user_register_failed), Toast.LENGTH_LONG)
                toast.show()
            }
            else if(registerViewModel.stateAddUser.value != 0){
                val toast = Toast.makeText(requireContext(), getString(R.string.user_register_success), Toast.LENGTH_LONG)
                toast.show()
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_register_to_profile)
            }
        })

        return binding.root
    }
}