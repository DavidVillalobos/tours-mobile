package com.example.android.tours_mobile.fragments.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.databinding.FragmentProfileBinding
import com.example.android.tours_mobile.services.dto.UserDTO
import com.example.android.tours_mobile.viewmodels.profile.ProfileViewModel
import com.google.gson.Gson


class ProfileFragment : Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        if(sharedPreferences?.contains("user")!!) {
            val json: String? = sharedPreferences?.getString("user", "")
            val user: UserDTO = Gson().fromJson(json, UserDTO::class.java)
            binding.textViewIdentification.text = user.identification
            binding.textViewName.text = user.name
            binding.textViewLastName.text = user.lastName
            binding.textViewEmail.text = user.email
            binding.textViewCountry.text = user.country.name
            binding.textViewBirthday.text = user.birthday
            binding.screenLogin.visibility = INVISIBLE;
            binding.screenProfile.visibility = VISIBLE;
        }else {
            binding.screenLogin.visibility = VISIBLE;
            binding.screenProfile.visibility = INVISIBLE;
        }
        binding.buttonLogOut.setOnClickListener{logout()}
        binding.buttonHidePasswordLogin.isChecked = false
        binding.buttonHidePasswordLogin.setOnClickListener {
            binding.editTextPassword.transformationMethod =
                if (binding.buttonHidePasswordLogin.isChecked) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
        }
        binding.buttonSignUp.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_profile_to_register)
        }
        binding.buttonLogIn.isEnabled = false
        binding.editTextEmail.addTextChangedListener { validateFields(); }
        binding.editTextPassword.addTextChangedListener { validateFields(); };
        binding.buttonLogIn.setOnClickListener { login() }
        validateFields()
        return binding.root
    }

    private fun validateFields() {
        var isValid : Boolean = true;
        if (binding.editTextEmail.text.isEmpty()) {
            binding.editTextEmail.error = getString(R.string.required_email)
            isValid = false
        }else{
            val email = binding.editTextEmail.text.toString().trim()
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editTextEmail.error = getString(R.string.required_format_email)
                isValid = false
            }
        }
        if (binding.editTextPassword.text.isEmpty()) {
            binding.editTextPassword.error = getString(R.string.required_password)
            isValid = false
        }else {
            val password = binding.editTextPassword.text.toString().trim()
            if (password.length < 8) {
                binding.editTextPassword.error = getString(R.string.required_password_extension)
                isValid = false
            } else if(!password.matches(Regex(".*\\d.*"))) {
                binding.editTextPassword.error = getString(R.string.required_password_numbers)
                isValid = false
            } else if (!password.matches(Regex(".*[a-z].*"))) {
                binding.editTextPassword.error = getString(R.string.required_password_lower)
                isValid = false
            } else if (!password.matches(Regex(".*[A-Z].*"))) {
                binding.editTextPassword.error = getString(R.string.required_password_upper)
                isValid = false
            } else if (!password.matches(Regex(".*\\W.*"))) {
                binding.editTextPassword.error = getString(R.string.required_password_especial_character)
                isValid = false
            }
        }
        binding.buttonLogIn.isEnabled = isValid
    }

    private fun login(){
        val user = UserDTO(
            binding.editTextEmail.text.toString().trim(),
            binding.editTextPassword.text.toString().trim()
        );
        profileViewModel.authenticateUser(user)
        profileViewModel.currentUser.observe(viewLifecycleOwner, {
            if(it != null && it.id != -1){
                Log.d("TAG_", "Login Successful!")
                val toast = Toast.makeText(requireContext(), getString(R.string.user_login_success), Toast.LENGTH_LONG)
                toast.show()
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_profile_to_explore)
                // Save current user in SharePreferences
                val editor = sharedPreferences?.edit()
                editor?.putString("user", Gson().toJson(it))
                editor?.commit()
            }else if(it != null){
                Log.d("TAG_", "An error happened!")
                val toast = Toast.makeText(requireContext(), getString(R.string.user_login_failed), Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    private fun logout(){
        val editor = sharedPreferences?.edit()
        editor?.remove("user")
        editor?.commit()
        binding.screenLogin.visibility = VISIBLE;
        binding.screenProfile.visibility = INVISIBLE;
        validateFields();
    }

}