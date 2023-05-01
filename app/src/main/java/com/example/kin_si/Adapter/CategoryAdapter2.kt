package com.example.kin_si.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kin_si.R


class CategoryAdapter2(private val pageLists: List<CategoryViewItem>) : RecyclerView.Adapter<CategoryViewHolder2>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_view_template, parent, false)
        return CategoryViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: CategoryViewHolder2, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}