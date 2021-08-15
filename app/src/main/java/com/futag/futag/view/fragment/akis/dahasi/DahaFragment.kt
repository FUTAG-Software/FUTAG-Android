package com.futag.futag.view.fragment.akis.dahasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.MoreoverRecyclerAdapter
import com.futag.futag.databinding.FragmentMoreoverBinding
import com.futag.futag.model.MoreoverItemModel

class DahaFragment : Fragment() {

    private var _binding: FragmentMoreoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemList: ArrayList<MoreoverItemModel>
    private lateinit var adapter: MoreoverRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreoverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemList = ArrayList()

        val birimlerimiz = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.futag_icon)!!,
            getString(R.string.our_units)
        )
        val biziOyla = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.star)!!,
            getString(R.string.rate_us)
        )
        val bildirimler = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.bell)!!,
            getString(R.string.notification)
        )
        val geriBildirim = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.mail_contact)!!,
            getString(R.string.feedback)
        )
        val hakkimizda = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.info)!!,
            getString(R.string.about_us)
        )
        val ayarlar = MoreoverItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.moreover_settings)!!,
            getString(R.string.settings)
        )

        itemList.add(birimlerimiz)
        itemList.add(biziOyla)
        itemList.add(bildirimler)
        itemList.add(geriBildirim)
        itemList.add(hakkimizda)
        itemList.add(ayarlar)

        adapter = MoreoverRecyclerAdapter(this ,requireContext(), itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}