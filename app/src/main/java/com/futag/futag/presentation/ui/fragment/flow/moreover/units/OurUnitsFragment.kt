package com.futag.futag.presentation.ui.fragment.flow.moreover.units

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futag.futag.databinding.FragmentOurUnitsBinding

class OurUnitsFragment : Fragment() {

    private var _binding: FragmentOurUnitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOurUnitsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foreignRelationsUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(1)
            findNavController().navigate(action)
        }

        binding.entrepreneurshipUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(2)
            findNavController().navigate(action)
        }

        binding.mediaUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(3)
            findNavController().navigate(action)
        }

        binding.projectRDUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(4)
            findNavController().navigate(action)
        }

        binding.socialResponsibilityUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(5)
            findNavController().navigate(action)
        }

        binding.softwareUnit.setOnClickListener {
            val action = OurUnitsFragmentDirections.actionBirimlerimizFToBirimDetayF(6)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}