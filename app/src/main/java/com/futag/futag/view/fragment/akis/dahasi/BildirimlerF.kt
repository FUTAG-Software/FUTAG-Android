package com.futag.futag.view.fragment.akis.dahasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.adapter.NotificationRecyclerAdapter
import com.futag.futag.databinding.FragmentNotificationBinding
import com.futag.futag.model.NotificationModel

class BildirimlerF : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var bildirimListesi: ArrayList<NotificationModel>
    private lateinit var adapter: NotificationRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bildirimListesi = ArrayList()

        if (bildirimListesi.isNullOrEmpty()){
            binding.recyclerView.visibility = View.INVISIBLE
            binding.textViewNoNotification.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textViewNoNotification.visibility = View.GONE
        }

        adapter = NotificationRecyclerAdapter(bildirimListesi)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}