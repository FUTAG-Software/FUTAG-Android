package com.futag.futag.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private val BEKLEME_SURESI = 1500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        Handler(Looper.getMainLooper()).postDelayed({
            if(onBoardingBitimi()){
                findNavController().navigate(R.id.action_splashFragment_to_girisKayitFragment)
            } else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        },BEKLEME_SURESI)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBoardingBitimi(): Boolean{
        val sharedPreference = requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("bitis",false)
    }

}