package com.example.kin_si.Adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.R


class PromotionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageView = view.findViewById<ImageView>(R.id.iv_promotion)

    fun bind(imageId: Int) {
        imageView.setImageResource(imageId)
    }
}