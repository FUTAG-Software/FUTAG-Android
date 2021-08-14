package com.futag.futag.view.fragment.akis.etkinlik

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.futag.futag.R
import com.futag.futag.adapter.EtkinliklerRecyclerAdapter
import com.futag.futag.databinding.FragmentEventBinding
import com.futag.futag.model.etkinlik.EtkinliklerModel
import com.futag.futag.viewmodel.AkisViewModel

class EtkinlikFragment : Fragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!
    private val etkinlikAdapter = EtkinliklerRecyclerAdapter(this, EtkinliklerModel())
    private lateinit var viewModel: AkisViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(AkisViewModel::class.java)
        viewModel.etkinlikVerileriniAl()

        binding.recyclerView.adapter = etkinlikAdapter

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.etkinlikVerileri.observe(viewLifecycleOwner,{ etkinlikler ->
            etkinlikler?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                etkinlikAdapter.etkinlikleriGuncelle(it)
            }
        })
        viewModel.etkinlikError.observe(viewLifecycleOwner,{ error ->
            error?.let {
                if (it){
                    binding.textViewErrorMessage.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewErrorMessage.visibility = View.GONE
                }
            }
        })
        viewModel.etkinlikYukleniyor.observe(viewLifecycleOwner, { yukleniyor ->
            yukleniyor?.let {
                if (it){
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