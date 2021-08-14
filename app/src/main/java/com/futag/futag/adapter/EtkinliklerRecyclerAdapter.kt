package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.EventsRecyclerRowBinding
import com.futag.futag.model.etkinlik.EtkinliklerModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir
import com.futag.futag.view.fragment.akis.etkinlik.EtkinlikFragmentDirections

class EtkinliklerRecyclerAdapter(
    private val parentFragment: Fragment,
    private val etkinlikListesi: EtkinliklerModel
    ): RecyclerView.Adapter<EtkinliklerRecyclerAdapter.EtkinliklerViewHolder>() {

    inner class EtkinliklerViewHolder(val itemBinding: EventsRecyclerRowBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtkinliklerViewHolder {
        val binding = EventsRecyclerRowBinding.inflate(
            LayoutInflater.from(parentFragment.requireContext()),parent,false
        )
        return EtkinliklerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EtkinliklerViewHolder, position: Int) {
        val canliVeri = etkinlikListesi[position]

        holder.itemBinding.cardViewEtkinlik.setOnClickListener {
            val action = EtkinlikFragmentDirections.actionEtkinlikFragmentToEtkinlikDetayFragment(canliVeri)
            parentFragment.findNavController().navigate(action)
        }

        holder.itemBinding.imageView.resimleriUrlIleGetir(
            canliVeri.image,
            placeholderProgressBar(parentFragment.requireContext())
        )
        holder.itemBinding.textViewTitle.text = canliVeri.title
    }

    override fun getItemCount(): Int {
        return etkinlikListesi.size
    }

    fun etkinlikleriGuncelle(yeniEtkinlikListesi: EtkinliklerModel){
        etkinlikListesi.clear()
        etkinlikListesi.addAll(yeniEtkinlikListesi)
        notifyDataSetChanged()
    }

}