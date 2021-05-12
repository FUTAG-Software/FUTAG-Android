package com.futag.futag.view.fragment.giris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentGirisKayitBinding

class GirisKayitFragment : Fragment() {

    private var _binding : FragmentGirisKayitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGirisKayitBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGirisYap.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_girisYapFragment)
        }

        binding.buttonKayitOl.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_kayitOlFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}