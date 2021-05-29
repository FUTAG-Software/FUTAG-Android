package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.BlogRecyclerRowBinding
import com.futag.futag.model.BlogModel

class BlogRecyclerAdapter(private val blogListesi: ArrayList<BlogModel>): RecyclerView.Adapter<BlogRecyclerAdapter.BlogViewHolder>() {

    class BlogViewHolder(val itemBinding: BlogRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = BlogRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.itemBinding.blogKonu.text = blogListesi[position].konu
        holder.itemBinding.blogYazar.text = blogListesi[position].yazar
    }

    override fun getItemCount(): Int {
        return blogListesi.size
    }

}