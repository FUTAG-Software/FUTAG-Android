package com.futag.futag.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.DahasiRecyclerRowBinding
import com.futag.futag.model.DahasiItemModel

class DahasiRecyclerAdapter(val context: Context, val itemList: ArrayList<DahasiItemModel>)
    : RecyclerView.Adapter<DahasiRecyclerAdapter.DahasiViewHolder>() {

    class DahasiViewHolder(val itemBinding: DahasiRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DahasiViewHolder {
        val binding = DahasiRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DahasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DahasiViewHolder, position: Int) {
        holder.itemBinding.imageViewYuvarlakResim.setImageDrawable(itemList[position].resim)
        holder.itemBinding.textViewBasliklar.text = itemList[position].baslik
        holder.itemBinding.cardView.setOnClickListener {
            println("Tiklandi")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}