package com.example.kin_si.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.R


class DiscoverViewAdapter(private val pageLists: List<DiscoverViewItem>) : RecyclerView.Adapter<DiscoverViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_template, parent, false)
        return DiscoverViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: DiscoverViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }

}