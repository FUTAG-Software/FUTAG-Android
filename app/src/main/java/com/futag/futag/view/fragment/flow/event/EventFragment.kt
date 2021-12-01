package com.futag.futag.view.fragment.flow.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.adapter.EventRecyclerAdapter
import com.futag.futag.databinding.FragmentEventBinding
import com.futag.futag.model.event.EventsModel

class EventFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private val eventAdapter = EventRecyclerAdapter(this, EventsModel())
    private lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[EventViewModel::class.java]
        viewModel.getEvents()

        binding.recyclerView.adapter = eventAdapter

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.eventDatas.observe(viewLifecycleOwner, { events ->
            events?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                eventAdapter.updateEvent(it)
            }
        })
        viewModel.eventError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.eventLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    binding.textViewErrorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}