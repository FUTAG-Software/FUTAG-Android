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
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.view.fragment.flow.blog.BlogFragmentDirections

class BlogRecyclerAdapter(private val parentFragment: Fragment, private val blogList: BlogModel)
    : RecyclerView.Adapter<BlogRecyclerAdapter.BlogViewHolder>() {

    class BlogViewHolder(val itemBinding: BlogRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = BlogRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val currentData = blogList[position]

        if(currentData.featuredImage != null){
            holder.itemBinding.blogImage.fetchImagesWithUrl(currentData.featuredImage.large,
                placeholderProgressBar(parentFragment.requireContext()))
        } else {
            holder.itemBinding.blogImage.setImageDrawable(
                ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.error)
            )
        }

        holder.itemBinding.blogTitle.text = currentData.title
        holder.itemBinding.blogAuthor.text = currentData.author
        holder.itemBinding.layout.setOnClickListener {
            val action = BlogFragmentDirections.actionBlogFragmentToBlogDetayFragment(currentData)
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return blogList.size
    }

    fun updateBlogs(newBlogList: BlogModel){
        blogList.clear()
        blogList.addAll(newBlogList)
        notifyDataSetChanged()
    }

}