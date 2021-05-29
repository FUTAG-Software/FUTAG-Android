package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.BildirimlerRecyclerRowBinding
import com.futag.futag.model.BildirimlerModel

class BildirimlerRecyclerAdapter(val bildirimListesi: ArrayList<BildirimlerModel>)
    :RecyclerView.Adapter<BildirimlerRecyclerAdapter.BildirimlerViewHolder>() {

    class BildirimlerViewHolder(val itemBinding: BildirimlerRecyclerRowBinding)
        :RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BildirimlerViewHolder {
        val binding = BildirimlerRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BildirimlerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BildirimlerViewHolder, position: Int) {
        holder.itemBinding.textViewBildirimMetni.text = bildirimListesi[position].bildirimMetni
        holder.itemBinding.textViewBildirimZamani.text = bildirimListesi[position].bildirimZamani
    }

    override fun getItemCount(): Int {
        return bildirimListesi.size
    }

}