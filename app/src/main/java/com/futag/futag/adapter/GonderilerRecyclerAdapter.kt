package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.GonderiRecyclerRowBinding
import com.futag.futag.model.GonderiModel
import com.futag.futag.view.fragment.akis.ev.EvFragmentDirections

class GonderilerRecyclerAdapter(private val parentFragment: Fragment)
    : RecyclerView.Adapter<GonderilerRecyclerAdapter.GonderilerViewHolder>() {

    private var gonderListesi = emptyList<GonderiModel>()

    inner class GonderilerViewHolder(val itemBinding: GonderiRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GonderilerViewHolder {
        val binding = GonderiRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GonderilerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GonderilerViewHolder, position: Int) {
        val canliVeri = gonderListesi[position]
        when(canliVeri.resim){
            1 -> {
                holder.itemBinding.imageViewGonderi.setImageDrawable(
                    ContextCompat.getDrawable(parentFragment.requireContext(), R.drawable.denemeresim1))
            }
            2 -> {
                holder.itemBinding.imageViewGonderi.setImageDrawable(
                    ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.denemeresim2))
            }
            3 -> {
                holder.itemBinding.imageViewGonderi.setImageDrawable(
                    ContextCompat.getDrawable(parentFragment.requireContext(),R.drawable.denemeresim3))
            }
        }
        holder.itemBinding.textViewBaslik.text = canliVeri.baslik
        holder.itemBinding.textViewAciklama.text = canliVeri.detay
        holder.itemBinding.cardView.setOnClickListener {
            val action = EvFragmentDirections.actionEvFragmentToGonderiDetayFragment(canliVeri)
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return gonderListesi.size
    }

    fun gonderiGuncelle(gonderiListe: List<GonderiModel>){
        gonderListesi = gonderiListe
        notifyDataSetChanged()
    }

}