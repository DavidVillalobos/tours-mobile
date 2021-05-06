package com.example.android.tours_mobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.android.tours_mobile.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val buttonRegister: Button = root.findViewById(R.id.Registrarse)
        buttonRegister.setOnClickListener{
            requireActivity().setContentView(R.layout.fragment_register);
        }
        return root
    }
}