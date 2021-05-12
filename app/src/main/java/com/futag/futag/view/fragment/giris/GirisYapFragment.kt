package com.futag.futag.view.fragment.giris

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentGirisYapBinding

class GirisYapFragment : Fragment() {

    private var _binding: FragmentGirisYapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGirisYapBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGirisYap.setOnClickListener {

        }

        binding.textViewKayitOl.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_kayitOlFragment)
        }

        binding.textViewSifremiUnuttum.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_sifremiUnuttumFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}