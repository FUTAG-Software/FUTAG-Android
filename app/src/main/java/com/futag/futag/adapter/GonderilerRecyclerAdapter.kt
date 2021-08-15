package com.futag.futag.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.futag.futag.R
import com.futag.futag.databinding.PostRecyclerRowBinding
import com.futag.futag.model.mainscreen.MainScreenModel
import com.futag.futag.util.placeholderProgressBar
import com.futag.futag.util.resimleriUrlIleGetir
import com.futag.futag.view.fragment.akis.ev.EvFragmentDirections

class GonderilerRecyclerAdapter(private val parentFragment: Fragment, private val gonderiListesi: MainScreenModel)
    : RecyclerView.Adapter<GonderilerRecyclerAdapter.GonderilerViewHolder>() {

    inner class GonderilerViewHolder(val itemBinding: PostRecyclerRowBinding)
        : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GonderilerViewHolder {
        val binding = PostRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GonderilerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GonderilerViewHolder, position: Int) {
        val canliVeri = gonderiListesi[position]

        if(canliVeri.featuredImage != null){
            holder.itemBinding.imageViewPost.resimleriUrlIleGetir(canliVeri.featuredImage.large,
                placeholderProgressBar(parentFragment.requireContext()))
        } else {
            holder.itemBinding.imageViewPost.setImageDrawable(
                ContextCompat.getDrawable(parentFragment.requireContext(), R.drawable.error)
            )
        }

        holder.itemBinding.textViewTitle.text = canliVeri.title
        holder.itemBinding.cardView.setOnClickListener {
            val action = EvFragmentDirections.actionEvFragmentToGonderiDetayFragment(canliVeri)
            parentFragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return gonderiListesi.size
    }

    fun gonderiGuncelle(yeniGonderiListe: MainScreenModel){
        gonderiListesi.clear()
        gonderiListesi.addAll(yeniGonderiListe)
        notifyDataSetChanged()
    }

}