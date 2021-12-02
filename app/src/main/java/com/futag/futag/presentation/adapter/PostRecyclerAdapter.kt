package com.futag.futag.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.PostRecyclerRowBinding
import com.futag.futag.model.post.PostModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.presentation.ui.fragment.flow.home.HomeFragmentDirections
import com.futag.futag.util.fetchImagesWithUrl

class PostRecyclerAdapter(private val parentFragment: Fragment, private val postList: PostModel)
    : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {

    inner class PostViewHolder(val itemBinding: PostRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentData = postList[position]

        if(currentData.featuredImage != null){
            holder.itemBinding.imageViewPost.fetchImagesWithUrl(currentData.featuredImage.large,
                placeholderProgressBar(parentFragment.requireContext()))
        } else {
            holder.itemBinding.imageViewPost.setImageDrawable(
                ContextCompat.getDrawable(parentFragment.requireContext(), R.drawable.error)
            )
        }

        holder.itemBinding.textViewTitle.text = currentData.title
        holder.itemBinding.cardView.setOnClickListener {
            val action = HomeFragmentDirections.actionEvFragmentToGonderiDetayFragment(currentData)
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun updatePost(newPostList: PostModel){
        postList.clear()
        postList.addAll(newPostList)
        notifyDataSetChanged()
    }

}