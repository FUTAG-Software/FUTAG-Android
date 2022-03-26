package com.futag.futag.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.PostRecyclerRowBinding
import com.futag.futag.model.post.PostModelItem
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.listener.PostAdapterClickListener
import com.futag.futag.util.placeholderProgressBar

class PostRecyclerAdapter(
    private val context: Context,
    private val clickListener: PostAdapterClickListener,
) : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<PostModelItem>() {
        override fun areItemsTheSame(oldItem: PostModelItem, newItem: PostModelItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostModelItem, newItem: PostModelItem): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var postList: List<PostModelItem>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    inner class PostViewHolder(val itemBinding: PostRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            PostRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentData = postList[position]

        if (currentData.featuredImage != null) {
            holder.itemBinding.imageViewPost.fetchImagesWithUrl(currentData.featuredImage.large,
                placeholderProgressBar(context))
        } else {
            holder.itemBinding.imageViewPost.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.error)
            )
        }

        holder.itemBinding.apply {
            textViewTitle.text = currentData.title
            cardView.setOnClickListener {
                clickListener.clickListener(currentData)
            }
        }
    }

    override fun getItemCount(): Int = postList.size

}