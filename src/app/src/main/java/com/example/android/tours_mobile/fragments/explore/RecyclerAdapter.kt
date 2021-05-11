package com.example.android.tours_mobile.fragments.explore

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tours_mobile.R


class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var photos :  MutableList<String> = mutableListOf();
    private var places :  MutableList<String> = mutableListOf();
    private var names :  MutableList<String> = mutableListOf();
    private var reviews :  MutableList<Int> = mutableListOf();
    private var prices :  MutableList<Float> = mutableListOf();
    private var ratings :  MutableList<Float> = mutableListOf();

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.photo)
        var itemPlace: TextView = itemView.findViewById(R.id.place)
        var itemName: TextView = itemView.findViewById(R.id.tour)
        var itemRating: RatingBar = itemView.findViewById(R.id.rating)
        var itemPrice: TextView = itemView.findViewById(R.id.price)
        var itemReviews: TextView = itemView.findViewById(R.id.reviews)
    }

    constructor(photos : MutableList<String>,
                places : MutableList<String>,
                names : MutableList<String>,
                ratings : MutableList<Float>,
                prices : MutableList<Float>,
                reviews : MutableList<Int>,
    ) : this() {
        this.photos = photos
        this.places = places
        this.names = names
        this.ratings = ratings
        this.prices = prices
        this.reviews = reviews
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return names.size;
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val decodedString: ByteArray = Base64.decode(photos[position], Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        holder.itemImage.setImageBitmap(decodedByte)
        holder.itemPlace.text = places[position]
        holder.itemName.text = names[position]
        holder.itemRating.rating = ratings[position]
        holder.itemPrice.text = "${prices[position]}"
        holder.itemReviews.text = "${reviews[position]}"
    }

}