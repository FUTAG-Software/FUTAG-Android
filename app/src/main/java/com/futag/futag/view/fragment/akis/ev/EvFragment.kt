package com.futag.futag.view.fragment.akis.ev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.futag.futag.R
import com.futag.futag.adapter.GonderilerRecyclerAdapter
import com.futag.futag.databinding.FragmentEvBinding
import com.futag.futag.model.anasayfa.AnaSayfaModel
import com.futag.futag.viewmodel.AkisViewModel

class EvFragment : Fragment() {

    private var _binding: FragmentEvBinding? = null
    private val binding get() = _binding!!
    private lateinit var sliderListesi: ArrayList<SlideModel>
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
        viewModel = ViewModelProvider(requireActivity()).get(AkisViewModel::class.java)
        viewModel.anaSayfaVerileriniAl()

        sliderListesi = ArrayList()
        sliderDoldur()

        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                when(position){
                    0 -> {
                        findNavController().navigate(R.id.action_evFragment_to_webSitesiFragment)
                    }
                    1 -> {

                    }
                    2 -> {

                    }
                }
            }
        })

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = gonderiAdapter

        observeLiveData()
    }

    private fun sliderDoldur(){
        sliderListesi.add(SlideModel("https://pbs.twimg.com/media/E2f1rgLWYAg-fOW?format=jpg&name=large"))
        sliderListesi.add(SlideModel("https://static.daktilo.com/sites/77/uploads/2019/07/11/large/kocaelinin-turistik-yerler-1511338290-3585-1562846155.png"))
        sliderListesi.add(SlideModel("https://scontent.fist2-4.fna.fbcdn.net/v/t1.6435-9/149011924_3252086314892718_5970936174401427651_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=973b4a&_nc_ohc=CssAj9YuDkUAX8d95k2&_nc_ht=scontent.fist2-4.fna&oh=46ce83c9d2a570809283a59de64e819e&oe=60EE3479"))

        binding.imageSlider.setImageList(sliderListesi,ScaleTypes.FIT)
    }

    private fun observeLiveData(){
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}