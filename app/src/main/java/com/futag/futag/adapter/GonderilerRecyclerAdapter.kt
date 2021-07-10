package com.futag.futag.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.GonderiRecyclerRowBinding
import com.futag.futag.model.GonderiModel

class GonderilerRecyclerAdapter: RecyclerView.Adapter<GonderilerRecyclerAdapter.GonderilerViewHolder>() {

    private var gonderListesi = emptyList<GonderiModel>()

    inner class GonderilerViewHolder(val itemBinding: GonderiRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GonderilerViewHolder {
        val binding = GonderiRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GonderilerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GonderilerViewHolder, position: Int) {
        val canliVeri = gonderListesi[position]
        holder.itemBinding.imageViewGonderi.setImageDrawable(canliVeri.resim)
        holder.itemBinding.textViewBaslik.text = canliVeri.baslik
        holder.itemBinding.textViewAciklama.text = canliVeri.detay
    }

    override fun getItemCount(): Int {
        return gonderListesi.size
    }

    fun gonderiGuncelle(gonderiListe: List<GonderiModel>){
        gonderListesi = gonderiListe
        notifyDataSetChanged()
    }

}