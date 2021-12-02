package com.futag.futag.presentation.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentThirdScreenBinding
import com.futag.futag.util.SharedPref

class ThirdScreen : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: SharedPref

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

        mySharedPref = SharedPref(requireContext())

        binding.lottieAnimation.setAnimation("onboarding3.json")
        binding.lottieAnimation.playAnimation()

        binding.buttonFinish.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_girisKayitFragment)
            isOnBoardingFinish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // onBoarding ekranlarinin tekrar gosterilmemesi icin fonksiyon
    private fun isOnBoardingFinish(){
        mySharedPref.setOnBoardingState(true)
    }

}