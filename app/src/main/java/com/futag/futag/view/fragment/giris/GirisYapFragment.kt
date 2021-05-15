package com.futag.futag.view.fragment.giris

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.futag.futag.R
import com.futag.futag.databinding.FragmentGirisYapBinding
import com.futag.futag.view.activity.AkisActivity
import com.futag.futag.viewmodel.KayitOlGirisYapViewModel

class GirisYapFragment : Fragment() {

    private var _binding: FragmentGirisYapBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KayitOlGirisYapViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(KayitOlGirisYapViewModel::class.java)

        binding.buttonGirisYap.setOnClickListener {
            if (veriGirisKontrolu()){
                val email = binding.editTextMail.text.toString()
                val sifre = binding.editTextSifre.text.toString()

                viewModel.girisOnayDurumu(email,sifre,requireContext())
                veriyiGozlemle()
            } else {
                Toast.makeText(requireContext(),R.string.bosluklari_doldurunuz,Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewKayitOl.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_kayitOlFragment)
        }

        binding.textViewSifremiUnuttum.setOnClickListener {
            findNavController().navigate(R.id.action_girisYapFragment_to_sifremiUnuttumFragment)
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
            animasyon?.let {
                if (it){
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.lottieAnimasyon.visibility = View.VISIBLE
                    animasyonuGoster()
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.lottieAnimasyon.visibility = View.GONE
                    animasyonuDurdur()
                }
            }
        })
        viewModel.veriOnayi.observe(viewLifecycleOwner, { veriOnayi ->
            veriOnayi?.let { onay ->
                if (onay){
                    activity?.let {
                        val intent = Intent(it, AkisActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }
            }
        })
    }

    private fun veriGirisKontrolu(): Boolean = binding.editTextMail.text.isNotEmpty()
            && binding.editTextSifre.text.isNotEmpty()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}