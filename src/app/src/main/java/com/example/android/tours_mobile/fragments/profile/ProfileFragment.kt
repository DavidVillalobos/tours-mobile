package com.example.android.tours_mobile.fragments.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R
import com.example.android.tours_mobile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(){

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.buttonHidePasswordLogin.isChecked = false
        binding.buttonHidePasswordLogin.setOnClickListener{
            binding.editTextPassword.transformationMethod =
            if(binding.buttonHidePasswordLogin.isChecked) HideReturnsTransformationMethod.getInstance()
            else PasswordTransformationMethod.getInstance()
        }
        binding.buttonSignUp.setOnClickListener{
            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_profile_to_register)
        }
        binding.buttonLogIn.isEnabled = false
        binding.editTextEmail.addTextChangedListener(editTextWatcher);
        binding.editTextPassword.addTextChangedListener(editTextWatcher);
        return binding.root
    }

    private var editTextWatcher: TextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            binding.buttonLogIn.isEnabled = !(TextUtils.isEmpty(binding.editTextEmail.text) or TextUtils.isEmpty(binding.editTextPassword.text));
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // TODO Auto-generated method stub
        }
        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
        }
    }
}