package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.databinding.ReklamlarRecyclerRowBinding
import com.futag.futag.model.reklam.ReklamlarModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir
import com.futag.futag.view.fragment.akis.ev.EvFragmentDirections

class ReklamlarRecyclerAdapter(private val reklamListesi: ReklamlarModel,private val parentFragment: Fragment)
    : RecyclerView.Adapter<ReklamlarRecyclerAdapter.ReklamlarViewHolder>() {

    inner class ReklamlarViewHolder(val binding: ReklamlarRecyclerRowBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReklamlarViewHolder {
        val binding = ReklamlarRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReklamlarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReklamlarViewHolder, position: Int) {
        val canliVeri = reklamListesi[position]
        holder.binding.imageView.resimleriUrlIleGetir(
            canliVeri.image,
            placeholderProgressBar(parentFragment.requireContext())
        )
        holder.binding.layout.setOnClickListener {
            val action = EvFragmentDirections.actionEvFragmentToWebSitesiFragment(canliVeri.redirectingLink)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int{
        return reklamListesi.size
    }

}