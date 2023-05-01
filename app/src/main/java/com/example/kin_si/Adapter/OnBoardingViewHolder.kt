package com.example.kin_si.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.R


class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textTitle = view.findViewById<TextView>(R.id.tvOnboardingTitle)
    private val textDescr = view.findViewById<TextView>(R.id.tvOnboardingDescr)
    private val imageView = view.findViewById<ImageView>(R.id.ivOnboarding)

    fun bind(onBoardingViewItem: OnBoardingViewItem) {
        textTitle.text = onBoardingViewItem.title
        textDescr.text = onBoardingViewItem.description
        imageView.setImageResource(onBoardingViewItem.imageId)
    }
}