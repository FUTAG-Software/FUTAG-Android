package com.futag.futag.view.fragment.akis.dahasi.birimlerimiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentOurUnitsBinding

class BirimlerimizF : Fragment() {

    private var _binding: FragmentOurUnitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOurUnitsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foreignRelationsUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(0)
            Navigation.findNavController(it).navigate(action)
        }

        binding.entrepreneurshipUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(1)
            Navigation.findNavController(it).navigate(action)
        }

        binding.mediaUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(2)
            Navigation.findNavController(it).navigate(action)
        }

        binding.projectRDUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(3)
            Navigation.findNavController(it).navigate(action)
        }

        binding.socialResponsibilityUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(4)
            Navigation.findNavController(it).navigate(action)
        }

        binding.softwareUnit.setOnClickListener {
            val action = BirimlerimizFDirections.actionBirimlerimizFToBirimDetayF(5)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}