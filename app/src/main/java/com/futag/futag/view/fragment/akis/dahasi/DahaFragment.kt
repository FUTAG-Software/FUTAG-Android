package com.futag.futag.view.fragment.akis.dahasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.DahasiRecyclerAdapter
import com.futag.futag.databinding.FragmentDahaBinding
import com.futag.futag.model.DahasiItemModel

class DahaFragment : Fragment() {

    private var _binding: FragmentDahaBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemList: ArrayList<DahasiItemModel>
    private lateinit var adapter: DahasiRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDahaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemList = ArrayList()

        val birimlerimiz = DahasiItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.futag_icon)!!,
            getString(R.string.birimlerimiz)
        )
        val biziOyla = DahasiItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.yildiz)!!,
            getString(R.string.bizi_oyla)
        )
        val bildirimler = DahasiItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.zil)!!,
            getString(R.string.bildirimler)
        )
        val geriBildirim = DahasiItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.mail_iletisim)!!,
            getString(R.string.geri_bildirim)
        )
        val hakkimizda = DahasiItemModel(
            ContextCompat.getDrawable(requireContext(),R.drawable.info)!!,
            getString(R.string.hakkimizda)
        )

        itemList.add(birimlerimiz)
        itemList.add(biziOyla)
        itemList.add(bildirimler)
        itemList.add(geriBildirim)
        itemList.add(hakkimizda)

        adapter = DahasiRecyclerAdapter(this ,requireContext(), itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}