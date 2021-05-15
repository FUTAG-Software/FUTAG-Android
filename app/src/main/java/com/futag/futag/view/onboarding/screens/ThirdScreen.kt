package com.futag.futag.view.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lottieAnimasyon.setAnimation("onboarding3.json")
        binding.lottieAnimasyon.playAnimation()

        binding.buttonBitir.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_girisKayitFragment)
            onBoardingBitimi()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // onBoarding ekranlarinin tekrar gosterilmemesi icin fonksiyon
    private fun onBoardingBitimi(){
        val sharedPreference = requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("bitis",true)
        editor.apply()
    }

}