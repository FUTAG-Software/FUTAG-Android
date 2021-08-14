package com.futag.futag.view.fragment.akis.ev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.futag.futag.adapter.GonderilerRecyclerAdapter
import com.futag.futag.adapter.ReklamlarRecyclerAdapter
import com.futag.futag.databinding.FragmentHomeBinding
import com.futag.futag.model.anasayfa.AnaSayfaModel
import com.futag.futag.util.LinePagerIndicatorDecoration
import com.futag.futag.viewmodel.AkisViewModel

class EvFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var reklamlarAdapter: ReklamlarRecyclerAdapter
    private lateinit var viewModel: AkisViewModel
    private val gonderiAdapter = GonderilerRecyclerAdapter(this, AnaSayfaModel())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val layoutManagerReklam = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        viewModel = ViewModelProvider(requireActivity()).get(AkisViewModel::class.java)
        viewModel.anaSayfaVerileriniAl()
        viewModel.reklamVerileriniAl()

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = gonderiAdapter
        binding.recyclerViewAds.layoutManager = layoutManagerReklam

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewAds)

        observeGonderiLiveData()
        observeReklamLiveData()

        binding.recyclerViewAds.addItemDecoration(LinePagerIndicatorDecoration())
    }

    private fun observeGonderiLiveData(){
        viewModel.anaSayfaVerileri.observe(viewLifecycleOwner, { bloglar ->
            bloglar?.let {
                binding.textViewErrorMessage.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                gonderiAdapter.gonderiGuncelle(it)
            }
        })
        viewModel.anaSayfaError.observe(viewLifecycleOwner, { error ->
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
        viewModel.anaSayfaYukleniyor.observe(viewLifecycleOwner, { yukleniyor ->
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

    private fun observeReklamLiveData(){
        viewModel.anaSayfaReklamVerileri.observe(viewLifecycleOwner, { reklamlar ->
            reklamlar?.let { reklamModel ->
                binding.progressBarSlider.visibility = View.INVISIBLE
                binding.recyclerViewAds.visibility = View.VISIBLE
                reklamlarAdapter = ReklamlarRecyclerAdapter(reklamModel,this)
                binding.recyclerViewAds.adapter = reklamlarAdapter
            }
        })
        viewModel.anaSayfaReklamError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.progressBarSlider.visibility = View.GONE
                    binding.recyclerViewAds.visibility = View.GONE
                }
            }
        })
        viewModel.anaSayfaReklamYukleniyor.observe(viewLifecycleOwner, { yukleniyor ->
            yukleniyor?.let {
                if (it){
                    binding.progressBarSlider.visibility = View.VISIBLE
                    binding.recyclerViewAds.visibility = View.GONE
                } else {
                    binding.progressBarSlider.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}