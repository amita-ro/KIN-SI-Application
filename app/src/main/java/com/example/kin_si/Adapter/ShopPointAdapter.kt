package com.example.kin_si.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.R


class ShopPointAdapter(private val pageLists: List<ShopPointViewItem>) : RecyclerView.Adapter<ShopPointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopPointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopcard_template, parent, false)
        return ShopPointViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: ShopPointViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}