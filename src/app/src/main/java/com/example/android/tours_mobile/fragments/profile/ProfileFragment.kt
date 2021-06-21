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
import androidx.annotation.Nullable
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.databinding.FragmentProfileBinding
import com.example.android.tours_mobile.services.dto.UserDTO
import com.example.android.tours_mobile.viewmodels.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson


class ProfileFragment : Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        profileViewModel.context = requireContext()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding.buttonLogOut.setOnClickListener{ logout() }
        binding.buttonLogIn.setOnClickListener { login() }

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

        profileViewModel.isValid.observe(viewLifecycleOwner, {
            binding.buttonLogIn.isEnabled = it
        })

        binding.editTextEmail.addTextChangedListener{
            profileViewModel.email.value = it.toString()
        }
        profileViewModel.emailErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextEmail.error = it
        })

        binding.editTextPassword.addTextChangedListener {
            profileViewModel.password.value = it.toString()
        }
        profileViewModel.passwordErrorMessage.observe(viewLifecycleOwner, {
            binding.editTextPassword.error = it
        })

        binding.editTextEmail.setText("")
        binding.editTextPassword.setText("")

        return binding.root
    }

    private fun login(){
        profileViewModel.authenticateUser()
        profileViewModel.currentUser.observe(viewLifecycleOwner, {
            if(it != null && it.id != -1){
                Log.d("TAG_", "Login Successful!")
                Snackbar.make(binding.root, R.string.user_login_success, Snackbar.LENGTH_LONG).show()
                Navigation.findNavController(requireView()).navigate(R.id.action_navigation_profile_to_explore)
                val editor = sharedPreferences?.edit()
                editor?.putString("user", Gson().toJson(it))
                editor?.apply()
            }else if(it != null){
                Log.d("TAG_", "An error happened!")
                Snackbar.make(binding.root, R.string.user_login_failed, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun logout(){
        val editor = sharedPreferences?.edit()
        editor?.remove("user")
        editor?.apply()
        binding.screenLogin.visibility = VISIBLE
        binding.screenProfile.visibility = INVISIBLE
    }

}