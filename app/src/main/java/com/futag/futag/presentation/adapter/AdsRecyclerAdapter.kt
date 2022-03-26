package com.futag.futag.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.AdsRecyclerRowBinding
import com.futag.futag.model.advertising.AdsModelItem
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.listener.AdsAdapterClickListener
import com.futag.futag.util.placeholderProgressBar

class AdsRecyclerAdapter(
    private val context: Context,
    private val clickListener: AdsAdapterClickListener,
) : RecyclerView.Adapter<AdsRecyclerAdapter.AdsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<AdsModelItem>() {
        override fun areItemsTheSame(oldItem: AdsModelItem, newItem: AdsModelItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AdsModelItem, newItem: AdsModelItem): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerDiffUtil = AsyncListDiffer(this, diffUtil)

    var adsList: List<AdsModelItem>
        get() = recyclerDiffUtil.currentList
        set(value) = recyclerDiffUtil.submitList(value)

    inner class AdsViewHolder(val binding: AdsRecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding =
            AdsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        val currentData = adsList[position]
        holder.binding.apply {
            imageView.fetchImagesWithUrl(currentData.image,
                placeholderProgressBar(context)
            )
            layout.setOnClickListener {
                clickListener.onClickItem(currentData)
            }
        }
    }

    override fun getItemCount(): Int = adsList.size

}