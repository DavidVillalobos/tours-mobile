package com.example.android.tours_mobile.fragments.explore

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tours_mobile.databinding.FragmentExploreBinding
import com.example.android.tours_mobile.services.dto.UserDTO
import com.example.android.tours_mobile.viewmodels.explore.ExploreViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class ExploreFragment : Fragment() {
    private var sharedPreferences: SharedPreferences? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var _binding: FragmentExploreBinding? = null
    private val explorerViewModel: ExploreViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.progressBar.bringToFront() // show always in front
        binding.editTextDeparture.setOnClickListener {
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
                        binding.editTextDeparture.setText("${year}-${fm}-${fd}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            binding.editTextArrival.text.clear()
            datePickerDialog!!.show()
        }
        binding.editTextArrival.setOnClickListener {
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
                        binding.editTextArrival.setText("${year}-${fm}-${fd}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            if(binding.editTextDeparture.text.toString() != "") {
                val df : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date : Date? = df.parse(binding.editTextDeparture.text.toString())
                datePickerDialog!!.datePicker.minDate = date!!.time
            }
            datePickerDialog!!.show()
        }
        binding.buttonSearch.setOnClickListener{ searchTours() }
        adapter = RecyclerAdapter(mutableListOf())
        layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        explorerViewModel.tours.observe(viewLifecycleOwner, {
            adapter = RecyclerAdapter(it)
            binding.recyclerView.adapter = adapter
        })
        explorerViewModel.stateSearch.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = when (it) {
                // 0 -> not search yet
                1 -> View.VISIBLE // in search
                // 2 -> found success
                // 3 -> not found tours
                else -> View.GONE
            }
        })
        return binding.root
    }

    private fun searchTours() {
        val place : String? = if(binding.editTextPlace.text.isEmpty()) null
        else binding.editTextPlace.text.toString().trim()
        val departure : String? = if(binding.editTextDeparture.text.isEmpty()) null
        else binding.editTextDeparture.text.toString().trim()
        val arrival : String? = if(binding.editTextArrival.text.isEmpty()) null
        else binding.editTextArrival.text.toString().trim()
        val idUser : Int? = if(sharedPreferences?.contains("user")!!) {
            val json: String? = sharedPreferences?.getString("user", "")
            val user: UserDTO = Gson().fromJson(json, UserDTO::class.java)
            user.id
        }else{
            null
        }
        explorerViewModel.searchTours(place, departure, arrival, idUser)
    }
}