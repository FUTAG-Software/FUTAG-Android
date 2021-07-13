package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.BlogRecyclerRowBinding
import com.futag.futag.model.blog.BlogModel
import com.futag.futag.view.fragment.akis.blog.BlogFragmentDirections
import com.squareup.picasso.Picasso

class BlogRecyclerAdapter(val parentFragment: Fragment, private val blogListesi: BlogModel)
    : RecyclerView.Adapter<BlogRecyclerAdapter.BlogViewHolder>() {

    class BlogViewHolder(val itemBinding: BlogRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = BlogRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val canliVeri = blogListesi[position]

        if(canliVeri.featuredImage != null){
            Picasso
                .get()
                .load(canliVeri.featuredImage.large)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.itemBinding.blogImage)
        } else {
            holder.itemBinding.blogImage.setImageDrawable(
                ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.error)
            )
        }

        holder.itemBinding.blogBaslik.text = canliVeri.title
        holder.itemBinding.blogYazar.text = canliVeri.author
        holder.itemBinding.layout.setOnClickListener {
            val action = BlogFragmentDirections.actionBlogFragmentToBlogDetayFragment(canliVeri)
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return blogListesi.size
    }

}