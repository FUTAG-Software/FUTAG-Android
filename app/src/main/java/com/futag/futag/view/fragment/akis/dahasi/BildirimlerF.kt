package com.futag.futag.view.fragment.akis.dahasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.R
import com.futag.futag.adapter.BildirimlerRecyclerAdapter
import com.futag.futag.databinding.FragmentBildirimlerBinding
import com.futag.futag.model.BildirimlerModel

class BildirimlerF : Fragment() {

    private var _binding: FragmentBildirimlerBinding? = null
    private val binding get() = _binding!!
    private lateinit var bildirimListesi: ArrayList<BildirimlerModel>
    private lateinit var adapter: BildirimlerRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBildirimlerBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bildirimListesi = ArrayList()

        val x = BildirimlerModel("Adem Geldi ve yazi yazdi","23-02-2021")
        val y = BildirimlerModel("Bas kalem muduru gorevden ayrildi xd","22-02-2021")
        val z = BildirimlerModel("Basi agiranlarr","21-02-2021")
        val a = BildirimlerModel("Etkinlik yapildi","20-02-2021")
        val b = BildirimlerModel("Izinler degistirildi","12-02-2021")
        val c = BildirimlerModel("Sok haberler geldi ve rektor istifa etti","10-02-2021")

        bildirimListesi.add(x)
        bildirimListesi.add(y)
        bildirimListesi.add(z)
        bildirimListesi.add(a)
        bildirimListesi.add(b)
        bildirimListesi.add(c)

        adapter = BildirimlerRecyclerAdapter(bildirimListesi)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}