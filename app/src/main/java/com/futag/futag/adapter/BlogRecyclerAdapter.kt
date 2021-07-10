package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.BlogRecyclerRowBinding
import com.futag.futag.databinding.FragmentBlogBinding
import com.futag.futag.model.BlogModel
import com.futag.futag.view.fragment.akis.blog.BlogFragmentDirections

class BlogRecyclerAdapter(val parentFragment: Fragment, private val blogListesi: ArrayList<BlogModel>): RecyclerView.Adapter<BlogRecyclerAdapter.BlogViewHolder>() {

    class BlogViewHolder(val itemBinding: BlogRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = BlogRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        when(blogListesi[position].resim){
            1 -> {
                holder.itemBinding.blogImage.setImageDrawable(ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.deneme_blog))
            }
            2 -> {
                holder.itemBinding.blogImage.setImageDrawable(ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.denemeresim1))
            }
            3 -> {
                holder.itemBinding.blogImage.setImageDrawable(ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.denemeresim2))
            }
            4 -> {
                holder.itemBinding.blogImage.setImageDrawable(ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.denemeresim3))
            }
        }

        holder.itemBinding.blogKonu.text = blogListesi[position].konu
        holder.itemBinding.blogYazar.text = blogListesi[position].yazar
        holder.itemBinding.layout.setOnClickListener {
            val action = BlogFragmentDirections.actionBlogFragmentToBlogDetayFragment(blogListesi[position])
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return blogListesi.size
    }

}