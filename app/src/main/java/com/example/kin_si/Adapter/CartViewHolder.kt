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
import java.security.AccessController.getContext


class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val foodname= view.findViewById<TextView>(R.id.foodname)
    private val food_pic = view.findViewById<ImageView>(R.id.food_pic)
    private val price = view.findViewById<TextView>(R.id.price)
    private val context = view.context

    fun bind(cartViewItem: CartViewItem) {
        foodname.text = cartViewItem.Foodname
        Glide.with(context).load(cartViewItem.Imageurl).into(food_pic)
        price.text = "$ " + cartViewItem.Price.toString()

        }
    }

