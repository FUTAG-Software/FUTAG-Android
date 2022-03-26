package com.futag.futag.presentation.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentSplashBinding
import com.futag.futag.util.Constants.SPLASH_SCREEN_TIME
import com.futag.futag.util.SharedPref

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        mySharedPref = SharedPref(requireContext())

        // Giriste kullaniciyi bekletme
        Handler(Looper.getMainLooper()).postDelayed({
            if (mySharedPref.loadOnBoardingState()) {
                findNavController().navigate(R.id.action_splashFragment_to_girisKayitFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, SPLASH_SCREEN_TIME)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}