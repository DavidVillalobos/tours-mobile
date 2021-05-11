package com.example.android.tours_mobile.fragments.profile

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ToggleButton
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.android.tours_mobile.R


class RegisterFragment : Fragment() {

    private var datePickerDialog: DatePickerDialog? = null
    private lateinit var editTextBirthday : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var toggleEyeButton : ToggleButton

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        toggleEyeButton = view.findViewById(R.id.button_hide_password_register)
        editTextBirthday = view.findViewById(R.id.edit_text_birthday)
        editTextPassword = view.findViewById(R.id.edit_text_password_register)

        toggleEyeButton.isChecked = false
        toggleEyeButton.setOnClickListener{
            if(toggleEyeButton.isChecked){
                editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        editTextBirthday.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)
            val mMonth: Int = c.get(Calendar.MONTH)
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        editTextBirthday.setText("${dayOfMonth}/${monthOfYear + 1}/${year}")
                    }, mYear, mMonth, mDay)
            datePickerDialog!!.show()
        }
        return view;
    }
}