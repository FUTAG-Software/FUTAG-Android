package com.futag.futag.view.fragment.flow.moreover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.futag.futag.adapter.NotificationRecyclerAdapter
import com.futag.futag.databinding.FragmentNotificationBinding
import com.futag.futag.model.NotificationModel

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationList: ArrayList<NotificationModel>
    private lateinit var adapter: NotificationRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationList = ArrayList()

        if (notificationList.isNullOrEmpty()) {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.textViewNoNotification.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textViewNoNotification.visibility = View.GONE
        }

        adapter = NotificationRecyclerAdapter(notificationList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}