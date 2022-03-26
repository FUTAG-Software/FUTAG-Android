package com.futag.futag.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.EventsRecyclerRowBinding
import com.futag.futag.model.event.EventsModelItem
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.listener.EventAdapterClickListener
import com.futag.futag.util.placeholderProgressBar

class EventRecyclerAdapter(
    private val context: Context,
    private val onClickListener: EventAdapterClickListener,
) : RecyclerView.Adapter<EventRecyclerAdapter.EventsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<EventsModelItem>() {
        override fun areItemsTheSame(oldItem: EventsModelItem, newItem: EventsModelItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EventsModelItem,
            newItem: EventsModelItem,
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var events: List<EventsModelItem>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    class EventsViewHolder(val itemBinding: EventsRecyclerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = EventsRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val currentData = events[position]

        holder.itemBinding.apply {
            cardViewEtkinlik.setOnClickListener {
                onClickListener.onClickToEventItem(currentData)
            }
            imageView.fetchImagesWithUrl(
                currentData.image,
                placeholderProgressBar(context)
            )
            textViewTitle.text = currentData.title
        }

    }

    override fun getItemCount(): Int = events.size

}