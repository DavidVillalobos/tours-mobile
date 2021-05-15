package com.example.android.tours_mobile.fragments.explore

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tours_mobile.databinding.FragmentExploreBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


class ExploreFragment : Fragment() {

    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var datePickerDialog: DatePickerDialog? = null
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.editTextDeparture.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        binding.editTextDeparture.setText("${dayOfMonth}-${monthOfYear+1}-${year}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog!!.show()
        }
        binding.editTextArrival.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            datePickerDialog = DatePickerDialog(requireContext(),
                    { _, year, monthOfYear, dayOfMonth ->
                        binding.editTextArrival.setText("${dayOfMonth}-${monthOfYear+1}-${year}")
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
            datePickerDialog!!.show()
        }
        val photos :  MutableList<String> = mutableListOf();
        val places :  MutableList<String> = mutableListOf();
        val names :  MutableList<String> = mutableListOf();
        val prices :  MutableList<Float> = mutableListOf();
        val ratings :  MutableList<Float> = mutableListOf();
        val reviews :  MutableList<Int> = mutableListOf();

        try {
            val data = JSONObject(getToursFromJsonAssets())
            val tours: JSONArray = data.getJSONArray("tours")
            for (i in 0 until tours.length()) {
                val tour = tours.getJSONObject(i)
                val images: JSONArray = tour.getJSONArray("images")
                photos.add(images.getJSONObject(0).getString("photo")) // Get Main Photo
                names.add(tour.getString("name"))
                places.add(tour.getString("completePlace"))
                reviews.add(tour.getInt("reviews"));
                prices.add(tour.getString("price").toFloat())
                ratings.add(tour.getString("rating").toFloat())
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        adapter = RecyclerAdapter(photos, places, names, ratings, prices, reviews)
        layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    private fun getToursFromJsonAssets(): String {
        return try {
            val file: InputStream = requireActivity().assets.open("tours.json")
            val size = file.available()
            val buffer = ByteArray(size)
            file.read(buffer)
            file.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            "{}"
        }
    }

}