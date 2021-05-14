package com.futag.futag.view.fragment.giris

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentGirisKayitBinding
import com.futag.futag.view.activity.AkisActivity
import com.futag.futag.viewmodel.KayitOlGirisYapViewModel

class GirisKayitFragment : Fragment() {

    private var _binding : FragmentGirisKayitBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KayitOlGirisYapViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(KayitOlGirisYapViewModel::class.java)
        viewModel.otomatikGirisDurumu()
        veriyiGozlemle()

        binding.buttonGirisYap.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_girisYapFragment)
        }

        binding.buttonKayitOl.setOnClickListener {
            findNavController().navigate(R.id.action_girisKayitFragment_to_kayitOlFragment)
        }

    }

    private fun animasyonuGoster(){
        binding.lottieAnimasyon.setAnimation("ziplayanarianimation.json")
        binding.lottieAnimasyon.playAnimation()
    }

    private fun animasyonuDurdur(){
        binding.lottieAnimasyon.cancelAnimation()
    }

    private fun veriyiGozlemle(){
        viewModel.animasyon.observe(viewLifecycleOwner, { animasyon ->
            animasyon?.let { deger ->
                if (deger){
                    viewModel.girisVarMi.observe(viewLifecycleOwner, { giris ->
                        giris?.let {
                            if (it){
                                animasyonuGoster()
                                binding.constraintLayout.visibility = View.INVISIBLE
                                binding.lottieAnimasyon.visibility = View.VISIBLE
                                activity?.let { activity ->
                                    val intent = Intent(activity, AkisActivity::class.java)
                                    activity.startActivity(intent)
                                    activity.finish()
                                }
                            } else {
                                animasyonuDurdur()
                                binding.constraintLayout.visibility = View.VISIBLE
                                binding.lottieAnimasyon.visibility = View.GONE
                            }
                        }
                    })
                } else {
                    animasyonuDurdur()
                    binding.constraintLayout.visibility = View.VISIBLE
                    binding.lottieAnimasyon.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}