package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.EventsRecyclerRowBinding
import com.futag.futag.model.event.EventsModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.view.fragment.akis.etkinlik.EtkinlikFragmentDirections

class EventRecyclerAdapter(
    private val parentFragment: Fragment,
    private val eventList: EventsModel
    ): RecyclerView.Adapter<EventRecyclerAdapter.EventsViewHolder>() {

    inner class EventsViewHolder(val itemBinding: EventsRecyclerRowBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = EventsRecyclerRowBinding.inflate(
            LayoutInflater.from(parentFragment.requireContext()),parent,false
        )
        return EventsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val currentData = eventList[position]

        holder.itemBinding.cardViewEtkinlik.setOnClickListener {
            val action = EtkinlikFragmentDirections.actionEtkinlikFragmentToEtkinlikDetayFragment(currentData)
            parentFragment.findNavController().navigate(action)
        }

        holder.itemBinding.imageView.fetchImagesWithUrl(
            currentData.image,
            placeholderProgressBar(parentFragment.requireContext())
        )
        holder.itemBinding.textViewTitle.text = currentData.title
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEvent(newEventList: EventsModel){
        eventList.clear()
        eventList.addAll(newEventList)
        notifyDataSetChanged()
    }

}