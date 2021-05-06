package com.example.android.tours_mobile.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.tours_mobile.R


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var buttonSigIn : Button
    private lateinit var buttonLogIn : Button
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var toggleEyeButton : ToggleButton

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        toggleEyeButton = view.findViewById(R.id.button_hide_password_login)
        buttonSigIn = view.findViewById(R.id.button_sign_in)
        buttonLogIn = view.findViewById(R.id.button_log_in)
        editTextEmail = view.findViewById(R.id.edit_text_email)
        editTextPassword = view.findViewById(R.id.edit_text_password)

        toggleEyeButton.isChecked = false
        toggleEyeButton.setOnClickListener{
            if(toggleEyeButton.isChecked){
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        buttonSigIn.setOnClickListener(this)
        buttonLogIn.isEnabled = false
        editTextEmail.addTextChangedListener(editTextWatcher);
        editTextPassword.addTextChangedListener(editTextWatcher);
        return view
    }

    override fun onClick(view: View?) {
        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_profile_to_register)
    }

    private var editTextWatcher: TextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonLogIn.isEnabled = !(TextUtils.isEmpty(editTextEmail.text) or TextUtils.isEmpty(editTextPassword.text));
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // TODO Auto-generated method stub
        }
        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
        }
    }
}