package com.futag.futag.view.fragment.akis.ev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.futag.futag.R
import com.futag.futag.databinding.FragmentGonderiDetayBinding

class GonderiDetayFragment : Fragment() {

    private val args by navArgs<GonderiDetayFragmentArgs>()
    private var _binding: FragmentGonderiDetayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGonderiDetayBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val canliVeri = args.canliGonderi
        binding.textViewYazar.text = canliVeri.yazar
        binding.textViewBaslik.text = canliVeri.baslik
        binding.textViewDetay.text = canliVeri.detay

        when(canliVeri.resim){
            1 -> {
                binding.imageViewResim.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.denemeresim1))
            }
            2 -> {
                binding.imageViewResim.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.denemeresim2))
            }
            3 -> {
                binding.imageViewResim.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.denemeresim3))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}