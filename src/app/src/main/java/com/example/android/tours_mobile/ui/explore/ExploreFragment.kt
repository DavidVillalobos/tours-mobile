package com.example.android.tours_mobile.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tours_mobile.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class ExploreFragment : Fragment() {

    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private var layoutManager : RecyclerView.LayoutManager? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_explore, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val photos :  MutableList<String> = mutableListOf();
        val places :  MutableList<String> = mutableListOf();
        val names :  MutableList<String> = mutableListOf();
        val prices :  MutableList<Float> = mutableListOf();
        val ratings :  MutableList<Float> = mutableListOf();
        val reviews :  MutableList<Int> = mutableListOf();

        try {
            val data = JSONObject(getJsonFromAssets("tours.json"))
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
        val layoutManager : LinearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        return view
    }

    private fun getJsonFromAssets(fileName: String): String {
        return try {
            val file: InputStream = requireActivity().assets.open(fileName)
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