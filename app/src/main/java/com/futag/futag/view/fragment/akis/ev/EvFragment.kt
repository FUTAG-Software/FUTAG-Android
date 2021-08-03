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
import com.futag.futag.databinding.FragmentEvBinding
import com.futag.futag.model.anasayfa.AnaSayfaModel
import com.futag.futag.util.LinePagerIndicatorDecoration
import com.futag.futag.viewmodel.AkisViewModel

class EvFragment : Fragment() {

    private var _binding: FragmentEvBinding? = null
    private val binding get() = _binding!!
    private lateinit var reklamlarAdapter: ReklamlarRecyclerAdapter
    private lateinit var viewModel: AkisViewModel
    private val gonderiAdapter = GonderilerRecyclerAdapter(this, AnaSayfaModel())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEvBinding.inflate(inflater, container, false)
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
        binding.recyclerViewReklam.layoutManager = layoutManagerReklam

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewReklam)

        observeGonderiLiveData()
        observeReklamLiveData()

        binding.recyclerViewReklam.addItemDecoration(LinePagerIndicatorDecoration())
    }

    private fun observeGonderiLiveData(){
        viewModel.anaSayfaVerileri.observe(viewLifecycleOwner, { bloglar ->
            bloglar?.let {
                binding.textViewHataMesaji.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                gonderiAdapter.gonderiGuncelle(it)
            }
        })
        viewModel.anaSayfaError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.textViewHataMesaji.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.textViewHataMesaji.visibility = View.GONE
                }
            }
        })
        viewModel.anaSayfaYukleniyor.observe(viewLifecycleOwner, { yukleniyor ->
            yukleniyor?.let {
                if (it){
                    binding.textViewHataMesaji.visibility = View.GONE
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
                binding.recyclerViewReklam.visibility = View.VISIBLE
                reklamlarAdapter = ReklamlarRecyclerAdapter(reklamModel,this)
                binding.recyclerViewReklam.adapter = reklamlarAdapter
            }
        })
        viewModel.anaSayfaReklamError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it){
                    binding.progressBarSlider.visibility = View.GONE
                    binding.recyclerViewReklam.visibility = View.GONE
                }
            }
        })
        viewModel.anaSayfaReklamYukleniyor.observe(viewLifecycleOwner, { yukleniyor ->
            yukleniyor?.let {
                if (it){
                    binding.progressBarSlider.visibility = View.VISIBLE
                    binding.recyclerViewReklam.visibility = View.GONE
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