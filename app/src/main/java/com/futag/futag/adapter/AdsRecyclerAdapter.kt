package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.AdsRecyclerRowBinding
import com.futag.futag.model.advertising.AdsModel
import com.futag.futag.util.fetchImagesWithUrl
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.view.fragment.flow.home.HomeFragmentDirections

class AdsRecyclerAdapter(private val adsList: AdsModel, private val parentFragment: Fragment)
    : RecyclerView.Adapter<AdsRecyclerAdapter.AdsViewHolder>() {

    inner class AdsViewHolder(val binding: AdsRecyclerRowBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = AdsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        val currentData = adsList[position]
        holder.binding.imageView.fetchImagesWithUrl(
            currentData.image,
            placeholderProgressBar(parentFragment.requireContext())
        )
        holder.binding.layout.setOnClickListener {
            val action = HomeFragmentDirections.actionEvFragmentToWebSitesiFragment(currentData.redirectingLink)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int{
        return adsList.size
    }

}