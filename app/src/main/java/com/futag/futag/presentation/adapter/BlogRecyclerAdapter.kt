package com.futag.futag.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.BlogRecyclerRowBinding
import com.futag.futag.model.blog.BlogModelItem
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.listener.BlogAdapterClickListener
import com.futag.futag.util.placeholderProgressBar

class BlogRecyclerAdapter(
    private val context: Context,
    private val clickListener: BlogAdapterClickListener
) : RecyclerView.Adapter<BlogRecyclerAdapter.BlogViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<BlogModelItem>() {
        override fun areItemsTheSame(oldItem: BlogModelItem, newItem: BlogModelItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BlogModelItem, newItem: BlogModelItem): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var blogList: List<BlogModelItem>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    class BlogViewHolder(val itemBinding: BlogRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding =
            BlogRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val currentData = blogList[position]

        if (currentData.featuredImage != null) {
            holder.itemBinding.blogImage.fetchImagesWithUrl(
                currentData.featuredImage.large,
                placeholderProgressBar(context)
            )
        } else {
            holder.itemBinding.blogImage.setImageDrawable(
                ContextCompat.getDrawable(context, R.drawable.error)
            )
        }

        holder.itemBinding.apply {
            blogTitle.text = currentData.title
            blogAuthor.text = currentData.author
            layout.setOnClickListener {
                clickListener.onClickListener(currentData)
            }
        }
    }

    override fun getItemCount(): Int = blogList.size

}