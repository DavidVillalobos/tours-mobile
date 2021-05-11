package com.example.android.tours_mobile.fragments.reservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.tours_mobile.R

class ReservationsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_reservations, container, false)
        //val textView: TextView = root.findViewById(R.id.text_reservations)
        return root
    }
}