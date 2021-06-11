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
import com.example.android.tours_mobile.services.dto.TourDTO


class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var tours :  List<TourDTO> = listOf();

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.photo)
        var itemPlace: TextView = itemView.findViewById(R.id.place)
        var itemName: TextView = itemView.findViewById(R.id.tour)
        var itemRating: RatingBar = itemView.findViewById(R.id.rating)
        var itemPrice: TextView = itemView.findViewById(R.id.price)
        var itemReviews: TextView = itemView.findViewById(R.id.reviews)
        var itemDuration: TextView = itemView.findViewById(R.id.duration)
    }

    constructor(tours : List<TourDTO>) : this() {
        this.tours = tours
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tours.size;
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val decodedString: ByteArray = Base64.decode(tours[position].images!![0].photo, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        holder.itemImage.setImageBitmap(decodedByte)
        holder.itemPlace.text = tours[position].completePlace
        holder.itemName.text = tours[position].name
        holder.itemRating.rating = tours[position].rating
        holder.itemPrice.text = "${tours[position].price}"
        holder.itemReviews.text = "${tours[position].reviews}"
        holder.itemDuration.text = tours[position].duration.subSequence(0, 5);
    }

}