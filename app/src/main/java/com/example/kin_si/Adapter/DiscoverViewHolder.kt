package com.example.kin_si.Adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kin_si.LocationFragment
import com.example.kin_si.R
import com.example.kin_si.utils.GlobalBox


class DiscoverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val chefname = view.findViewById<TextView>(R.id.chefname)
    private val foodname = view.findViewById<TextView>(R.id.foodname)
    private val iv_profile = view.findViewById<ImageView>(R.id.iv_profile)
    private val food_pic = view.findViewById<ImageView>(R.id.food_pic)
    private val location = view.findViewById<Button>(R.id.bt_location)
    private val price = view.findViewById<Button>(R.id.bt_buy)
    private val context = view.context

    fun bind(discoverViewItem: DiscoverViewItem) {
        chefname.text = discoverViewItem.Chefname
        foodname.text = discoverViewItem.Foodname
        Glide.with(context).load(discoverViewItem.ChefImage).into(iv_profile)
        Glide.with(context).load(discoverViewItem.Imageurl).into(food_pic)
        price.text = "$ " + discoverViewItem.Price.toString()
        price.setOnClickListener {
            GlobalBox.Foodname.add(discoverViewItem.Foodname)
            GlobalBox.Imageurl.add(discoverViewItem.Imageurl)
            GlobalBox.Price.add(discoverViewItem.Price)
            Toast.makeText(context,"Your order is add to cart.",Toast.LENGTH_SHORT).show()
        }
        location.setOnClickListener {
            GlobalBox.Latitude = discoverViewItem.Latitude
            GlobalBox.Longitude = discoverViewItem.Longitude
            (context as AppCompatActivity)?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fl_container_dis, LocationFragment())
                        .addToBackStack("DiscoverFragment")
                        .commit()
            }
        }
    }
}