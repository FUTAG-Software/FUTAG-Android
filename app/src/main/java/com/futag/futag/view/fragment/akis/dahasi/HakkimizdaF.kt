package com.futag.futag.view.fragment.akis.dahasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.futag.futag.R
import com.futag.futag.databinding.FragmentHakkimizdaBinding

class HakkimizdaF : Fragment() {

    private var _binding: FragmentHakkimizdaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHakkimizdaBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}