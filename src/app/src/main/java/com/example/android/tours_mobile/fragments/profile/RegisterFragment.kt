package com.example.android.tours_mobile.fragments.profile

import android.R
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
import android.widget.ArrayAdapter
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.android.tours_mobile.ServiceAdapter
import com.example.android.tours_mobile.databinding.FragmentRegisterBinding
import com.example.android.tours_mobile.services.dto.CountryDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        ServiceAdapter.getCountryService().getCountries().enqueue(object : Callback<List<CountryDTO>> {
            override fun onFailure(call: Call<List<CountryDTO>>, t: Throwable) {
                Log.d("TAG_", "An error happened!")
                val arrayAdapter = ArrayAdapter<CountryDTO>(requireContext(), R.layout.simple_spinner_item, emptyList())
                binding.spinnerCountry.adapter = arrayAdapter
            }
            override fun onResponse(
                call: Call<List<CountryDTO>>,
                response: Response<List<CountryDTO>>
            ) {
                Log.d("TAG_", response.body().toString())
                val arrayAdapter = ArrayAdapter<CountryDTO>(
                    requireContext(), R.layout.simple_spinner_item, response.body().orEmpty()
                )
                arrayAdapter.insert(CountryDTO(-1, getString(com.example.android.tours_mobile.R.string.country_origin), emptyList()), 0);
                arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spinnerCountry.adapter = arrayAdapter
                binding.spinnerCountry.prompt = getString(com.example.android.tours_mobile.R.string.label_country)
            }
        })

        binding.buttonHidePasswordRegister.isChecked = false
        binding.buttonHidePasswordRegister.setOnClickListener{
            binding.buttonHidePasswordRegister.transformationMethod =
                if(binding.buttonHidePasswordRegister.isChecked) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
        }
        binding.editTextBirthday.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        binding.editTextBirthday.setText("${dayOfMonth}-${monthOfYear + 1}-${year}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog!!.show()
        }
        return binding.root
    }
}